import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from Yield import Yield

cred = credentials.Certificate('unique-track-f112a-firebase-adminsdk-y0wbc-a39ee1c19c.json')
firebase_admin.initialize_app(cred, {
    'databaseURL' : 'https://unique-track-f112a-default-rtdb.firebaseio.com/'
})

"""ref = db.reference('Fake info')
ref.update({'UID' : 'sMV3KQmqPmVVtE5qEUKNsxISwTu2'})
ref.update({'Name' : '김현빈'})
ref = db.reference('Fake info/Product2')
ref.update({'Product name' : 'Galacy S21', 'Register date' : '2021-05-12',
                         'Image' : 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdvxskfAdBw1Mjz2UDA0OsCmuZPVEjyinHrXDo_8BOZaRVdPnxG_MZ2XqOIKNFa4gaxBU&usqp=CAU'})"""


ref = db.reference('Products/Product1')
ref.update({'OwnerUID' : 'sMV3KQmqPmVVtE5qEUKNsxISwTu2'})
ref.update({'OwnerName' : '김현빈'})
ref.update({'ProductName' : 'Galacy S21', 'RegisterDate' : '2021-05-12',
                         'Image' : 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdvxskfAdBw1Mjz2UDA0OsCmuZPVEjyinHrXDo_8BOZaRVdPnxG_MZ2XqOIKNFa4gaxBU&usqp=CAU'})
ref.update({'Explanation' :
            '2010년 갤럭시 S를 시작으로, 매해 상반기에 공개된 삼성전자의 안드로이드 플래그십 스마트폰 시리즈인 갤럭시 S 시리즈의 2021년형 모델이자 12번째 모델 중 기본모델'})
y = Yield('2022-05-12', 'sMV3KQmqPmVVtE5qEUKNsxISwTu1')
ref = db.reference('Products/Product1/양도내역/'+y.getDate())
ref.update({'UID' : y.getUID()})

ref = db.reference('Products/Product2')
ref.update({'OwnerUID' : 'p2Oo3Sv6QFVh5wHYIMXpP4ZUAGg2'})
ref.update({'OwnerName' : '김길동'})
ref.update({'ProductName' : 'iPod pro 11'})
ref.update({'RegisterDate' : '2012-05-17'})
ref.update({'image' :
            'https://www.google.com/imgres?imgurl=https%3A%2F%2Fstore.storeimages.cdn-apple.com%2F8756%2Fas-images.apple.com%2Fis%2Fipad-pro-11-select-wifi-spacegray-202104_GEO_KR_FMT_WHH%3Fwid%3D940%26hei%3D1112%26fmt%3Dp-jpg%26qlt%3D95%26.v%3D1617928128000&imgrefurl=https%3A%2F%2Fwww.apple.com%2Fkr%2Fshop%2Fbuy-ipad%2Fipad-pro%2F11%25ED%2598%2595-%25EB%2594%2594%25EC%258A%25A4%25ED%2594%258C%25EB%25A0%2588%25EC%259D%25B4-256gb-%25EC%258A%25A4%25ED%258E%2598%25EC%259D%25B4%25EC%258A%25A4-%25EA%25B7%25B8%25EB%25A0%2588%25EC%259D%25B4-wifi&tbnid=vds0wYf_pV-quM&vet=12ahUKEwj7_66Lxub3AhVETPUHHYPfAfMQMygBegUIARCMAg..i&docid=BZkTRs0pDXzXiM&w=940&h=1112&q=ipad%20pro%2011&ved=2ahUKEwj7_66Lxub3AhVETPUHHYPfAfMQMygBegUIARCMAg'
            })
ref.update({'Explanation' : '11형 iPad Pro 2세대의 후속작'})
y2 = Yield('2022-05-17', 'p2Oo3Sv6QFVh5wHYIMXpP4ZUAGg2')
ref = db.reference('Products/Product2/양도내역/'+y2.getDate())
ref.update({'UID' : y2.getUID()})

ref = db.reference('Products/Product3')
ref.update({'OwnerUID' : 'sMV3KQmqPmVVtE5qEUKNsxISwTu2'})
ref.update({'OwnerName' : '김현빈'})
ref.update({'ProductName' : 'KB Card'})
ref.update({'RegisterDate' : '2022-05-17'})
ref.update({'image' :
            'https://www.google.com/imgres?imgurl=https%3A%2F%2Fapi.card-gorilla.com%3A8080%2Fstorage%2Fcard%2F552%2Fcard_img%2F21257%2F552card.png&imgrefurl=https%3A%2F%2Fm.card-gorilla.com%2Fcard%2Fdetail%2F552&tbnid=A5iE91MH9GshAM&vet=12ahUKEwjruInsyeb3AhXJTPUHHdOiAQYQMygGegUIARDgAQ..i&docid=M9l8KpPrjAJRGM&w=360&h=227&q=%EC%82%BC%EC%84%B1%EC%B9%B4%EB%93%9C&hl=ko&ved=2ahUKEwjruInsyeb3AhXJTPUHHdOiAQYQMygGegUIARDgAQ'
            })
ref.update({'Explanation' : 'KB국민은행에서 발급된 카드이며, KB국민은행 관련 서비스만 이용 가능'})
y3 = Yield('2022-05-17', 'p2Oo3Sv6QFVh5wHYIMXpP4ZUAGg2')
ref = db.reference('Products/Product3/양도내역/'+y3.getDate())
ref.update({'UID' : y3.getUID()})

ref = db.reference('Products/Product4')
ref.update({'OwnerUID' : 'p2Oo3Sv6QFVh5wHYIMXpP4ZUAGg2'})
ref.update({'OwnerName' : '김길동'})
ref.update({'ProductName' : 'Airpod 3rd generation'})
ref.update({'RegisterDate' : '2022-05-17'})
ref.update({'image' :
            'https://www.google.com/imgres?imgurl=http%3A%2F%2Fimg.danawa.com%2Fprod_img%2F500000%2F143%2F429%2Fimg%2F4429143_1.jpg%3Fshrink%3D330%3A330%26_v%3D20200519100605&imgrefurl=http%3A%2F%2Fprod.danawa.com%2Finfo%2F%3Fpcode%3D4429143&tbnid=bg5rtjZlcINCXM&vet=12ahUKEwjEjODGzOb3AhXWFYgKHV3bAcAQMygAegUIARC9AQ..i&docid=w5wTBJVGGchdFM&w=330&h=330&q=%EC%97%90%EC%96%B4%ED%8C%9F%201%EC%84%B8%EB%8C%80&hl=ko&ved=2ahUKEwjEjODGzOb3AhXWFYgKHV3bAcAQMygAegUIARC9AQ'
            })
ref.update({'Explanation' : 'KST 2021년 10월 19일에 공개한 Apple의 AirPods 2세대의 후속작인 블루투스 무선 이어폰'})
y4 = Yield('2022-05-17', 'p2Oo3Sv6QFVh5wHYIMXpP4ZUAGg2')
ref = db.reference('Products/Product4/양도내역/'+y4.getDate())
ref.update({'UID' : y4.getUID()})
