package org.github.javiermf.fide2wikipedia.util

import java.io.File
import java.io.FileOutputStream

fun File.appenderStream() = FileOutputStream(this, true).bufferedWriter()