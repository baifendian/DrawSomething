#!/usr/bin/env python
#coding:utf-8


import sys, os
import json
reload(sys)
sys.setdefaultencoding( "utf-8")

sys.path.append('../')
sys.path.append('../../')
cur_dir = os.path.dirname( os.path.abspath(__file__)) or os.getcwd()
par_dir = os.path.dirname(os.path.dirname(cur_dir))

from django.http import HttpResponse        
from django.shortcuts import render

from django.views.decorators.csrf import csrf_exempt
from server import recognize
from ctypes import *


crypto_dll = cdll.LoadLibrary("libbfd_simple_crypto.so")
class Crypto(object):
    """ encrypt and decrypt string. """
    def __init__(self):
         pass

    def encrypt(self, raw):
        raw_len = len(raw)
        buf = create_string_buffer('\000' * (raw_len * 2))
        buf_len = c_int(0)
        crypto_dll.encrypt(c_char_p(raw), c_int(raw_len), buf, byref(buf_len))
        return buf.value[:buf_len.value]

    def decrypt(self, crypted):
        crypted_len = len(crypted)
        buf = create_string_buffer('\000' * crypted_len)
        dl = c_int(0)
        crypto_dll.decrypt(c_char_p(crypted), c_int(crypted_len), buf, byref(dl))
        return buf.value[:dl.value]


@csrf_exempt
def para(request):
    try:
        crypto = Crypto()
        decrypt_data=crypto.decrypt(request.body)
        json_data= json.loads(decrypt_data)
        s_type = json_data.get('s_type','')
        image_type=json_data.get('image_type','')
        image_path=json_data.get('image_path','')
        data=json_data.get('data','')
        result=recognize(s_type, image_type, image_path, data)
        encrypt_data=crypto.encrypt(json.dumps(result))
        out_data = HttpResponse(encrypt_data)
        return out_data


    except Exception as e:
        print "error",e
        return json.dumps({})



