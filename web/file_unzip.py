import zipfile
import os
import beauty_dir
import shutil

file_list = os.listdir(r'.')

for file_name in file_list:
    if os.path.splitext(file_name)[1] == '.zip':
        print (file_name)

        file_zip = zipfile.ZipFile(file_name, 'r')
        for file in file_zip.namelist():
            file_zip.extract(file, r'.')
        file_zip.close()
        os.remove(file_name)

        new_filename = (file_name.split('.')[0])
        path = './' + new_filename

        beauty_dir.beauty_dirlog(path)

        azip = zipfile.ZipFile('beauty_finished.zip', 'w')
        for current_path, subfolders, filesname in os.walk(path):
            print(current_path, subfolders, filesname)
            #  filesname是一个列表，我们需要里面的每个文件名和当前路径组合
            for file in filesname:
                # 将当前路径与当前路径下的文件名组合，就是当前文件的绝对路径
                azip.write(os.path.join(current_path, file))
        # 关闭资源
        azip.close()
        shutil.rmtree(path)
        os.remove('beauty_finished.zip')