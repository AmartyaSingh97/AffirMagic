package com.example.affirmagic.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.affirmagic.R
import com.example.affirmagic.databinding.BottomSheetLayoutBinding
import com.example.affirmagic.db.entity.AffirmationsEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream

class ModalBottomSheet(private val context: Context,private val affirmationsEntity: AffirmationsEntity) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)

        binding.apply {

            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.apply {
                strokeWidth = 5f
                centerRadius = 30f
                setColorSchemeColors(
                    R.color.primary
                )
                start()
            }

            Picasso.get().load(affirmationsEntity.dzImageUrl).placeholder(circularProgressDrawable).into(image)
            shareText.text = affirmationsEntity.text

            closeBTN.setOnClickListener {
                dismiss()
            }

            copyBTN.setOnClickListener {
                //copy text to clipboard
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("Copied Text", affirmationsEntity.text)
                clipboard.setPrimaryClip(clip)
                copyBTN.text = "Copied"
                copyBTN.setTextColor(resources.getColor(R.color.white))
                copyBTN.setBackgroundColor(resources.getColor(R.color.pink))
            }

            downloadBTN.setOnClickListener {
                downloadImage(context,affirmationsEntity.dzImageUrl,"${affirmationsEntity.themeTitle}.jpg")
            }

            whatsappBTN.setOnClickListener {
                //share image to whatsapp
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                shareIntent.putExtra(Intent.EXTRA_STREAM, affirmationsEntity.sharePrefix)
                shareIntent.setPackage("com.whatsapp")
                startActivity(shareIntent)
            }
        }

        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    private fun isAppInstalled(uri: String): Boolean {
        val pm = activity?.packageManager
        return try {
            pm?.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun downloadImage(context: Context, imageUrl: String, fileName: String) {
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    saveImage(context, resource, fileName)
                }
            })
    }

    private fun saveImage(context: Context, bitmap: Bitmap, fileName: String) {
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "YourAppName")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, fileName)
        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                Toast.makeText(context, "Image saved to ${file.absolutePath}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }
}