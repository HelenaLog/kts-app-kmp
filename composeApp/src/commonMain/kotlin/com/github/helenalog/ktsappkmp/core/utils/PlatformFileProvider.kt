package com.github.helenalog.ktsappkmp.core.utils

import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.io.Source

expect fun PlatformFile.toSource(): Source
