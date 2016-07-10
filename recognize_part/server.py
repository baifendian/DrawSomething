#coding:utf8
#Author: jialei.yan

import sys, os, binascii, random, re, logging
cur_dir = os.path.dirname(os.path.abspath(__file__)) or os.getcwd()

from utils import split_image_2, split_image_3, split_image_4
from img_scale import process_scale
from img_process import convert_img_to_binary
from process_calc import core_process

logging.basicConfig(level=logging.DEBUG,
                format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
                datefmt='%a, %d %b %Y %H:%M:%S',
                filename= cur_dir + '/split_image.log',
                filemode='w')



def recognize(s_type, image_type, image_path, data):
    '''
    :descroptions:
    :param s_type: the custom type of image, support 0、1、2、3、4
    :param image_type: the type of image
    :param image_path: if the file in the system, you can give the path of the image
    :param data: if the image not in the system, you can give the binary data after base64 decrypt
    :return:return a dict, thee keys is s_type、return_code、result after recognize
        when the return_code is '0' and the result of the recognize is not null, its successed.
        when return_code is '1', its error s_type
        when return_code is '2', its recognize image error
        when return_code is '3', its split image error
    '''
    result = {'s_type': s_type, 'return_code': '0', 'result':''}
    mypid = os.getpid()
    if s_type == '1':
        logging.info('get a task whos s_type is 1...')
        image_bin = binascii.a2b_base64(data)
        random_str = str(int(random.random()*1000))
        image_path = cur_dir + '/' + str(mypid) + '_' + random_str
        while os.path.exists(image_path[:-1]):
            random_str = str(int(random.random()*1000))
            image_path = cur_dir + '/' + str(mypid) + '_' + random_str
        f = open(image_path,'wb')
        f.write(image_bin)
        f.close()
        logging.info('saved image to ' + image_path)
        result_data = ''
        try:
            os.system('tesseract ' + image_path + ' ' + image_path + ' -l chi_sim -psm 6')
            result_file = open(image_path + '.txt','r')
            result_data = ''.join(result_file.readlines())
            result_file.close()
        except Exception,e:
            logging.info('error when recognize ' + image_path + ' with tesseract!!!')
            result['return_code'] = '2'
            return result
        result['result'] = unicode(result_data, 'utf-8')
        result['result'] = result['result'].encode('utf8')
        os.system('rm -rf ' + image_path)
        os.system('rm -rf ' + image_path + '.txt')
        result['result'] = core_process(result['result']) or result['result']
        logging.info('remove tmp file, finish recognize, the result of recognize is ' + str(result))
    elif s_type == '2':
        logging.info('get a task whos s_type is 2...')
        image_bin = binascii.a2b_base64(data)
        random_str = str(int(random.random()*1000))
        image_path = cur_dir + '/' + str(mypid) + '_' + random_str
        while os.path.exists(image_path[:-1]):
            random_str = str(int(random.random()*1000))
            image_path = cur_dir + '/' + str(mypid) + '_' + random_str
        f = open(image_path, 'wb')
        f.write(image_bin)
        f.close()
        logging.info('saved image to ' + image_path)

        try:
            img_list = split_image_2(image_path)
        except Exception, e:
            logging.info('error when split image ' + image_path)
            result['return_code'] = '3'
            return result
        result_data = ''
        try:
            for img_one in img_list:
                os.system('tesseract ' + img_one + ' ' + image_path + ' -l eng -psm 6')
                result_file = open(image_path + '.txt','r')
                result_data += ''.join(result_file.readlines())
                result_file.close()
        except Exception, e:
            logging.info('error when recognize ' + image_path + ' with recognize')
            result['return_code'] = '2'
            return result

        result['result'] = result_data
        os.system('rm -rf ' + image_path)
        os.system('rm -rf ' + image_path + '.txt')
        logging.info('remove tmp file, finish recognize, the result of recognize is ' + str(result))
    elif s_type == '3':
        logging.info('get a task whos s_type is 3...')
        random_str = str(int(random.random()*1000))
        img_split_file_path = cur_dir + '/img_data/raw/' + random_str+'/'
        image_path = cur_dir + '/' + str(mypid) + '_' + random_str
        while os.path.exists(img_split_file_path[:-1]):
            random_str = str(int(random.random()*1000))
            img_split_file_path = cur_dir + '/img_data/raw/' + random_str + '/'
            image_path = cur_dir + '/' + str(mypid) + '_' + random_str
        os.system('mkdir ' + img_split_file_path[:-1])
        result_file_path = cur_dir + '/result/' + random_str
        os.system('touch ' + result_file_path)
        image_bin = binascii.a2b_base64(data)

        f = open(image_path,'wb')
        f.write(image_bin)
        f.close()
        logging.info('saved image to ' + image_path)

        try:
            img_list = split_image_3(image_path, img_split_file_path)
        except Exception,e:
            logging.info('error when split image' + image_path)
            result['return_code'] = '3'
            return result
        try:
            outputfile = process_scale(img_split_file_path[:-1], random_str)
            outputfile = convert_img_to_binary(outputfile, random_str, True)
            os.system('export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/opt/gcc-4.8.5/lib64/:/opt/glibc-2.14/lib; python ' +
                      cur_dir + '/convolutional_eval.py ' + outputfile + ' ' + random_str + ' ' + str(len(img_list)))
            result_file = open(result_file_path,'r')
            result_data = ''.join(result_file.readlines())
            result_file.close()
        except Exception,e:
            logging.info('error when recognize image' + image_path)
            result['return_code'] = '2'
            return result
        result['result'] = result_data
        os.system('rm -rf ' + image_path)
        os.system('rm -rf ' + img_split_file_path[:-1])
        os.system('rm -rf ' + result_file_path)
        logging.info('remove tmp file, finish recognize, the result of recognize is ' + str(result))
    elif s_type == '4':
        logging.info('get a task whos s_type is 3...')
        random_str = str(int(random.random()*1000))
        img_split_file_path = cur_dir + '/img_data/raw/' + random_str + '/'
        image_path = cur_dir + '/' + str(mypid) + '_' + random_str
        while os.path.exists(img_split_file_path[:-1]):
            random_str = str(int(random.random()*1000))
            image_path = cur_dir + '/img_data/raw/' + random_str + '/'
        os.system('mkdir ' + img_split_file_path[:-1])
        result_file_path = cur_dir + '/result/' + random_str
        os.system('touch ' + result_file_path)
        image_bin = binascii.a2b_base64(data)

        f = open(image_path,'wb')
        f.write(image_bin)
        f.close()
        logging.info('saved image to ' + image_path)

        try:
            img_list = split_image_4(image_path, img_split_file_path)
        except Exception,e:
            logging.info('error when split image' + image_path)
            result['return_code'] = '3'
            return result
        try:
            outputfile = process_scale(img_split_file_path[:-1], random_str)
            outputfile = convert_img_to_binary(outputfile, random_str, True)
            os.system('export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/opt/gcc-4.8.5/lib64/:/opt/glibc-2.14/lib; python ' +
                      cur_dir + '/convolutional_eval.py ' + outputfile + ' ' + random_str +  ' ' + str(len(img_list)))
            result_file = open(result_file_path,'r')
            result_data = ''.join(result_file.readlines())
            result_file.close()
        except Exception,e:
            logging.info('error when recognize image' + image_path)
            result['return_code'] = '2'
            return result
        result['result'] = result_data
        os.system('rm -rf ' + img_split_file_path[:-1])
        os.system('rm -rf ' + result_file_path)
        os.system('rm -rf ' + image_path)
    else:
        logging.info('get error s_type!!!')
        result['return_code'] = '1'
        result['result'] = 'error s_type!!!'
    if result['return_code'] == '0' and result['result']:
        result['result'] = re.sub(r'\s','',result['result'])
    return result

if __name__ == '__main__':
    f = open('a.png', 'rb')
    a = f.read()
    f.close()
    b = recognize('3', 'png', '/path', binascii.b2a_base64(a))
    print b['result'].encode('utf8')
