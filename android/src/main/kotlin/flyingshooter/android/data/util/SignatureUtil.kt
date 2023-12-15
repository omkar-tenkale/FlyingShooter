package flyingshooter.android.data.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Locale

import android.annotation.SuppressLint;
import android.content.Context
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import java.security.cert.X509Certificate;
import java.util.ArrayList;


object SignatureUtil {
    fun selfSHA256Signatures(context: Context) = getSHA256Signatures(context.packageManager,context.packageName)

    fun getSHA256Signatures(
         pm: PackageManager,
         packageName: String
    ): List<String>? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val packageInfo: PackageInfo =
                    pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
                if (packageInfo?.signingInfo == null) {
                    return null
                }
                if (packageInfo.signingInfo.hasMultipleSigners()) {
                    toHexSignatures(packageInfo.signingInfo.apkContentsSigners)
                } else {
                    toHexSignatures(packageInfo.signingInfo.signingCertificateHistory)
                }
            } else {
                @SuppressLint("PackageManagerGetSignatures") val packageInfo: PackageInfo =
                    pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
                if (packageInfo.signatures == null || packageInfo.signatures.isEmpty() || packageInfo.signatures[0] == null
                ) {
                    null
                } else toHexSignatures(packageInfo.signatures)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun toHexSignature(signatureContent: ByteArray): String? {
        return try {
            val md = MessageDigest.getInstance("SHA256")
            md.update(signatureContent)
            val digest = md.digest()
            val toRet = StringBuilder()
            for (i in digest.indices) {
                if (i != 0) toRet.append(":")
                val b = digest[i].toInt() and 0xff
                val hex = Integer.toHexString(b)
                if (hex.length == 1) toRet.append("0")
                toRet.append(hex)
            }
            toRet.toString().uppercase(Locale.getDefault())
        } catch (e: NoSuchAlgorithmException) {
            null
        }
    }

    fun toHexSignature(cert: X509Certificate): String? {
        try {
            return toHexSignature(cert.getEncoded())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun toHexSignatures(sigList: Array<Signature>): kotlin.collections.List<String> {
        val hexSignaturesList: MutableList<String> = ArrayList()
        for (signature in sigList) {
            toHexSignature(signature.toByteArray())?.let {
                hexSignaturesList.add(it)

            }
        }
        return hexSignaturesList
    }
}