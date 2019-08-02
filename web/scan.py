import requests
url = 'https://www.baidu.com/s?wd=Coremail.%20%C2%A9%20Copyright%202000%20-%202015%20insite%3A*.edu.cn&rsv_spt=1&rsv_iqid=0xb41eadf600043a62&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_n=2&rsv_sug3=1'
headers = {
"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36",
}
print(requests.get(url,headers=headers).text)