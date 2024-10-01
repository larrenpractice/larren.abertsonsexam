package com.larren.abertsonsexam.presentation.util

val String.sanitizedInput: String
    get() = replace("[^a-zA-Z0-9 ]".toRegex(), "")