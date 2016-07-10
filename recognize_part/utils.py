#-*-coding:utf-8 -*-

from PIL import Image
import logging

def split_image_2(img_path):
    '''
    :description: deal the image whos s_type is 2
    :param img_path: the path of image
    :return: a list of the path of every splited image
    '''
    logging.info('spliting image' + img_path + ' whos s_type is 2...')
    img_dict = {}
    a = Image.open(img_path).convert('L')
    relation_dict = {}
    normal_pixel = 240
    pixels = set([])

    def put_a_to_dict(one_dict, key, value):
        """
        :description: put a dict to the dict of one_dict, the key is the param key ,the value is the dict of {value:0}
        :param one_dict: the dict where the key add to
        :paraam key: the key
        :param value: the key of {value:0}
        :return: no return
        """
        if type({}) == type(one_dict):
            if one_dict.has_key(key):
                one_dict[key][value] = 0
            else:
                one_dict[key] = {value:0}

    # around_pixel = [(-1,-1), (-1,0), (-1, 1), (0,-1), (0, 1), (1,-1), (1,0), (1, 1)]
    zone_p = [(-1, -1), (-1, 0), (0, -1), (1, -1)]
    other_p = [(-1, 1), (0, 1), (1, 0), (1, 1)]
    for i in range(0,a.size[0]):
        for j in range(0, a.size[1]):
            if i == 0 or j == 0 or i == a.size[0]-1 or j == a.size[1]-1 or a.getpixel((i,j)) > normal_pixel:
                a.putpixel((i, j), 255)
                continue
            other_pixel_count = 0
            for one_demo_p in zone_p:
                if a.getpixel((i+one_demo_p[0], j+one_demo_p[1])) < normal_pixel:
                    put_a_to_dict(relation_dict, i, j)
                    other_pixel_count += 1
            for one_demo_p in other_p:
                if a.getpixel((i+one_demo_p[0], j+one_demo_p[1])) < normal_pixel:
                    other_pixel_count += 1

            if other_pixel_count == 0:
                a.putpixel((i, j), 255)
            else:
                pixels.add((i, j))

    def get_other_p(zone_tmp, pixels_list, pointer_p):
        """
        :description:a recursion function, split all point to few zone, in every zone, the point is the next to the other
        :param zone_tmp: the zone of the midlle point
        :param pixels_list: the all rest points list 
        :param pointer_p: the midlle point 
        :return: no return
        """
        for i in zone_p + other_p:
            if (pointer_p[0] + i[0], pointer_p[1] + i[1]) in pixels_list:
                pixels_list.remove((pointer_p[0] + i[0], pointer_p[1] + i[1]))
                zone_tmp.append((pointer_p[0] + i[0], pointer_p[1] + i[1]))
                get_other_p(zone_tmp, pixels_list, (pointer_p[0]+i[0], pointer_p[1] + i[1]))

    zones = []
    while len(pixels):
        pointer_p = pixels.pop()
        zone_tmp = []
        zone_tmp.append(pointer_p)
        get_other_p(zone_tmp, pixels, pointer_p)
        zones.append(zone_tmp)

    zone_dict = {}
    count = 0
    for i in zones:
        if len(i) > 10:
            zone_dict[count] = i

            left_m = 99999
            up_m = 99999
            right_m = 0
            down_m = 0

            for j, k in i:
                left_m = left_m if left_m < j else j
                up_m = up_m if up_m < k else k
                right_m = right_m if right_m > j else j
                down_m = down_m if down_m > k else k
            f = Image.new('L', (right_m - left_m + 1, down_m - up_m + 1), 255)
            for j, k in i:
                f.putpixel((j-left_m, k-up_m), a.getpixel((j, k)))
            f.save(str(count) + '.png')
            img_dict[int((left_m + right_m)/2.0)] = str(count) + '.png'
            count += 1
    img_num_sorted = img_dict.keys()
    img_num_sorted.sort()
    img_list = [img_dict[img_num] for img_num in img_num_sorted]
    logging.info('split image' + img_path + ' done! get images ' + str(img_list))
    return img_list

def split_image_3(img_path, save_img_file_path):
    '''
    :description: deal with the image whos s_type is 3
    :param img_path: the path of the image
    :param save_img_file_path: the place where the splited image saved
    :return: a list of splited image path
    '''
    logging.info('spliting image' + img_path + ' whos s_type is 3...')
    img_list = []
    f = Image.open(img_path).convert('1')
    crop_f = Image.open(img_path).convert('L')

    pixel_list = []
    for i in range(f.size[0]):
        pixel_list.append(0)
        for j in range(f.size[1]):
            if f.getpixel((i,j)) != 255:
                pixel_list[i] += 1

    zones_list = []
    start_p = 0
    end_p = 0
    start_status = True
    for index, i in enumerate(pixel_list):
        if start_status:
            if i > 0:
                start_p = index
                end_p = index
                start_status = False
                zones_list.append([start_p,])
            else:
                pass
        else:
            if i > 0:
                end_p = index
            else:
                zones_list[-1].append(index)
                start_status = True
    if not start_status:
        zones_list[-1].append(f.size[0])
    zones_y_list = []
    for zone_one in zones_list:
        zones_y_list.append([])
        for j in range(f.size[1]):
            break_statu = False
            for i in range(zone_one[0], zone_one[1]):
                if f.getpixel((i,j)) != 255:
                    break_statu = True
                    zones_y_list[-1].append(j)
                    break
            if break_statu:
                break
        for j in range(f.size[1] - 1, -1, -1):
            break_statu = False
            for i in range(zone_one[0], zone_one[1]):
                if f.getpixel((i,j)) != 255:
                    break_statu = True
                    zones_y_list[-1].append(j + 1)
                    break
            if break_statu:
                break

    count = 0
    for index,i in enumerate(zones_list):

        if i[1]-i[0] < 5 and zones_y_list[index][1]-zones_y_list[index][0] < 5:
            count += 1
            continue
        if not zones_y_list[index] or len(zones_y_list[index]) != 2:
            count += 1
            continue
        tmp_image = crop_f.crop((i[0], zones_y_list[index][0], i[1], zones_y_list[index][1]))
        img_path_without_name = ''.join(img_path.split('/')[:-1])
        tmp_image.save(save_img_file_path + 'eng_' + str(count) + '_0.png')
        img_list.append(save_img_file_path + 'eng_' + str(count) + '_0.png')
        count += 1
    logging.info('split image' + img_path + ' done! get images ' + str(img_list))
    return img_list

def split_image_4(img_path, save_img_file_path):
    """
    :description: deal the image whos s_type is 4
    :param img_path: deal the image whos s_type is 4
    :param save_img_file_path: the place where the splited image saved
    :return: a list of splited image path
    """
    logging.info('spliting image' + img_path + ' whos s_type is 4...')
    im2 = Image.open(img_path).convert('L')
    min_num = 255
    for y in range(im2.size[1]):
        for x in range(im2.size[0]):
            if im2.getpixel((x,y)) < min_num:
                min_num = im2.getpixel((x, y))
    im3 = im2.copy()
    pixels = {}
    pixels2 = {}
    other_pixel_map = {
        1: ((-1,-1),(-1,0)),
        2: ((-1,0),(-1,1)),
        3: ((-1,1),(-1,2)),
        4: ((1,-1),(1,0)),
        5: ((1,0),(1,1)),
        6: ((1,1),(1,2)),
    }
    for y in range(0,im2.size[1]-1):
        for x in range(1,im2.size[0]):
            if im2.getpixel((x,y)) == min_num and im2.getpixel((x,y+1)) == min_num:
                other_black_list = []

                for i in other_pixel_map:
                    if x+other_pixel_map[i][0][0] < im2.size[0] \
                            and y + other_pixel_map[i][0][1] < im2.size[1] \
                            and x + other_pixel_map[i][1][0] < im2.size[0] \
                            and y + other_pixel_map[i][1][1] < im2.size[1]:
                        if im2.getpixel((x + other_pixel_map[i][0][0], y + other_pixel_map[i][0][1])) == min_num \
                                and im2.getpixel((x + other_pixel_map[i][1][0], y + other_pixel_map[i][1][1])) == min_num:
                            other_black_list.append(i)
                if len(other_black_list):
                    im3.putpixel((x, y), 0)
                    im3.putpixel((x, y + 1), 0)
                    if not pixels.get(x, ''):
                        pixels[x]=set([])
                    pixels[x].update([y, y+1])
                else:
                    pass

    x_list = pixels.keys()
    x_list.sort()

    for x in x_list:
        value = {
            'x': x,
            'set_left': set([]),
            'set_right': set([]),
        }

        if x - 1 in x_list:
            value['set_left'] = pixels[x] & pixels[x - 1]
        if x + 1 in x_list:
            value['set_right'] = pixels[x] & pixels[x + 1]

        real_set = set([])

        if not value['set_left']:
            value['set_left'] = value['set_right']
        if not value['set_right']:
            value['set_right'] = value['set_left']

        if value['set_left']:
            for left_p in value['set_left']:
                if left_p in value['set_right']:
                    real_set.update([left_p])
                if left_p + 1 in value['set_right']:
                    real_set.update([left_p, left_p + 1])
                if left_p - 1 in value['set_right']:
                    real_set.update([left_p, left_p - 1])

        if value['set_right']:
            for right_p in value['set_right']:
                if right_p in value['set_left']:
                    real_set.update([right_p])
                if right_p + 1 in value['set_left']:
                    real_set.update([right_p, right_p + 1])
                if right_p - 1 in value['set_left']:
                    real_set.update([right_p, right_p - 1])

        for y in pixels[x] - real_set:
            im3.putpixel((x, y), min_num)
        pixels2[x]=real_set

    im4=im3.copy()

    for x in pixels2:
        y_list = list(pixels2[x])
        y_list.sort()
        y_pointer = y_list[0] if y_list else 0
        y_list_tmp = [y_pointer]
        for y in y_list[1:]:
            if y == y_pointer + 1:
                y_list_tmp.append(y)
                y_pointer = y
                if y != y_list[-1]:
                    continue

            up_value = im4.getpixel((x, y_list_tmp[0]-1)) if y_list_tmp[0] - 1 >= 0 else 255
            down_value=im4.getpixel((x, y_list_tmp[-1]+1)) if y_list_tmp[-1] + 1<im4.size[1] else 255
            space = int((down_value-up_value)/len(y_list_tmp))

            for index, value in enumerate(y_list_tmp):
                up_value += space
                im4.putpixel((x, value), up_value)

            y_pointer = y
            y_list_tmp = [y]

    f = im4
    img_list = []

    pixel_list = []
    for i in range(f.size[0]):
        pixel_list.append(0)
        for j in range(f.size[1]):
            if f.getpixel((i,j)) != 255:
                pixel_list[i] += 1

    zones_list = []
    start_p = 0
    end_p = 0
    start_status = True
    for index, i in enumerate(pixel_list):
        if start_status:
            if i > 0:
                start_p = index
                end_p = index
                start_status = False
                zones_list.append([start_p,])
            else:
                pass
        else:
            if i > 0:
                end_p = index
            else:
                zones_list[-1].append(index)
                start_status = True
    if not start_status:
        zones_list[-1].append(f.size[0])
    zones_y_list = []
    for zone_one in zones_list:
        zones_y_list.append([])
        for j in range(f.size[1]):
            break_statu = False
            for i in range(zone_one[0], zone_one[1]):
                if f.getpixel((i, j)) != 255:
                    break_statu = True
                    zones_y_list[-1].append(j)
                    break
            if break_statu:
                break
        for j in range(f.size[1]-1, -1, -1):
            break_statu = False
            for i in range(zone_one[0], zone_one[1]):
                if f.getpixel((i, j)) != 255:
                    break_statu = True
                    zones_y_list[-1].append(j + 1)
                    break
            if break_statu:
                break

    count = 0
    for index, i in enumerate(zones_list):
        if zones_list[index][1] - zones_list[index][0] < 10 or zones_y_list[index][1] - zones_y_list[index][0] < 10:
            # count += 1
            continue
        if not zones_y_list[index] or len(zones_y_list[index]) != 2:
            # count += 1
            continue
        tmp_image = f.crop((zones_list[index][0], zones_y_list[index][0], zones_list[index][1], zones_y_list[index][1]))
        tmp_image.save(save_img_file_path + 'eng_' + str(count) + '_0.png')
        img_list.append(save_img_file_path + 'eng_' + str(count) + '_0.png')
        count += 1
    logging.info('split image' + img_path + ' done! get images ' + str(img_list))
    return img_list
