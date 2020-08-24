package my.luckydog.presentation.core

import android.app.Activity.RESULT_OK
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.copyToFile
import my.luckydog.presentation.core.extensions.createFile
import my.luckydog.presentation.core.extensions.fileMimeType
import my.luckydog.presentation.core.extensions.grantUriPermissions
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class RxImagePicker : Fragment() {

    companion object {
        const val CHOOSER = 111
        const val IMAGE_FILE_EXTENSION = "jpg"
        const val GRANT_FLAGS = FLAG_GRANT_WRITE_URI_PERMISSION or FLAG_GRANT_READ_URI_PERMISSION

        private val TAG = RxImagePicker::class.java.simpleName

        fun with(manager: FragmentManager): RxImagePicker {
            var fragment = manager.findFragmentByTag(TAG) as RxImagePicker?
            if (fragment == null) {
                fragment = RxImagePicker()
                manager.beginTransaction()
                    .add(fragment, TAG)
                    .commit()
            }
            return fragment
        }
    }

    private val authority: String by lazy { getString(R.string.file_provider_authority) }
    private val directory: String by lazy { getString(R.string.dir_image_picker) }

    private lateinit var attachedSubject: PublishSubject<Boolean>
    private lateinit var publishSubject: PublishSubject<File>
    private lateinit var canceledSubject: PublishSubject<Int>

    private lateinit var cameraPictureUri: Uri
    private lateinit var chooserTitle: String
    private var cameraPicturePath: String? = null

    fun chooseImage(title: String): Observable<File> {
        chooserTitle = title
        initSubjects()
        requestPickImage()
        return publishSubject.takeUntil(canceledSubject)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (::attachedSubject.isInitialized.not() or
            ::publishSubject.isInitialized.not() or
            ::canceledSubject.isInitialized.not()
        ) initSubjects()
        attachedSubject.onNext(true)
        attachedSubject.onComplete()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == CHOOSER) {
            if (data == null || data.data == null && data.clipData == null) {
                handleCameraResult()
            } else handleGalleryResult(data)
        } else canceledSubject.onNext(requestCode)
    }

    private fun initSubjects() {
        publishSubject = PublishSubject.create()
        attachedSubject = PublishSubject.create()
        canceledSubject = PublishSubject.create()
    }

    private fun requestPickImage() {
        if (!isAdded) attachedSubject.subscribe { pickImage() } else pickImage()
    }

    private fun pickImage() = startActivityForResult(getChooserIntent(chooserTitle), CHOOSER)

    private fun getChooserIntent(title: String): Intent? {
        val fileName = "${UUID.randomUUID()}." + IMAGE_FILE_EXTENSION
        val output = requireContext().cacheDir.createFile(directory, fileName) ?: return null

        cameraPicturePath = output.absolutePath
        cameraPictureUri = FileProvider.getUriForFile(requireContext(), authority, output)

        val cameraIntents = ArrayList<Intent>()
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        requireContext().packageManager.queryIntentActivities(captureIntent, 0).forEach {
            val intent = Intent(captureIntent).apply {
                component = ComponentName(it.activityInfo.packageName, it.activityInfo.name)
                setPackage(it.activityInfo.packageName)
                putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUri)
            }
            requireContext().grantUriPermissions(intent, cameraPictureUri, GRANT_FLAGS)
            cameraIntents.add(intent)
        }

        return createChooser(Intent(ACTION_PICK, EXTERNAL_CONTENT_URI).apply {
            putExtra(EXTRA_ALLOW_MULTIPLE, false)
        }, title).apply { putExtra(EXTRA_INITIAL_INTENTS, cameraIntents.toTypedArray()) }
    }

    private fun onImagePicked(file: File?) {
        if (file != null && file.length() > 0) {
            publishSubject.onNext(file)
        } else publishSubject.onError(Exception())
        publishSubject.onComplete()

        requireContext().revokeUriPermission(cameraPictureUri, GRANT_FLAGS)
    }

    private fun handleCameraResult() = onImagePicked(cameraPicturePath?.let { File(it) })

    private fun handleGalleryResult(data: Intent) {
        onImagePicked(when {
            data.data != null -> createCacheFile(data.data!!)
            data.clipData != null && data.clipData!!.itemCount > 0 -> {
                val imageUris = ArrayList<Uri>()
                with(data.clipData!!) { (0 until itemCount).forEach { imageUris.add(getItemAt(it).uri) } }
                createCacheFile(imageUris)
            }
            else -> null
        })
    }

    private fun createCacheFile(uri: Uri): File? {
        val extension = uri.fileMimeType(requireContext()) ?: return null
        val fileName = "${UUID.randomUUID()}.$extension"
        val outputFile = requireContext().cacheDir.createFile(directory, fileName) ?: return null
        return if (uri.copyToFile(requireContext(), outputFile)) outputFile else null
    }

    private fun createCacheFile(uris: List<Uri>): File? {
        uris.forEach { uri -> createCacheFile(uri)?.also { return it } }
        return null
    }
}