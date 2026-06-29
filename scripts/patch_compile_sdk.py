#!/usr/bin/env python3
# Auto-generated patch: fix compileSdk line in app/build.gradle.kts
# This file replaces the non-standard compileSdk block with canonical Kotlin DSL usage.

import io,sys

old = '''compileSdk { version = release(36) { minorApiLevel = 1 } }'''
new = 'compileSdk = 36'

p='app/build.gradle.kts'
text=open(p,'r',encoding='utf-8').read()
if old in text:
    text=text.replace(old,new)
    open(p,'w',encoding='utf-8').write(text)
    print('patched')
else:
    print('no change')
