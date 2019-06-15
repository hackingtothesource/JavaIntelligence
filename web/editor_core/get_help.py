from pyquery import PyQuery as pq
import  re

def get_help(doc_id):

    doc_id = doc_id.replace(".","\.")
    #print(doc_id)
    with open('help.html','r',encoding='utf-8') as file:
        html = file.read()

    # print(html)
    ans = ""
    doc=pq(html)
    tmp=doc(doc_id)
    for i in tmp.items():
        ans += (i.text())

    # print(tmp.siblings()('p'))


    while(tmp.next()):
        if('<h' not in str(tmp.next())):
            # ans += str((tmp.next()))
            html = tmp.next()
            ans += str(html)
            #print(html)
            tmp = tmp.next()
        else:
            return ans

# ans = get_help('#s4\.6\.1-vertical-whitespace')
# print(ans)