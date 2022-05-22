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
ref = db.reference('Fake info/product1')
ref.update({'Product name' : 'Galacy S21', 'Register date' : '2021-05-12',
                         'Image' : 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdvxskfAdBw1Mjz2UDA0OsCmuZPVEjyinHrXDo_8BOZaRVdPnxG_MZ2XqOIKNFa4gaxBU&usqp=CAU'})"""


ref = db.reference('Product1')
ref.update({'Owner UID' : 'sMV3KQmqPmVVtE5qEUKNsxISwTu2'})
ref.update({'Name' : '김현빈'})
ref.update({'Product name' : 'Galacy S21', 'Register date' : '2021-05-12',
                         'Image' : 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdvxskfAdBw1Mjz2UDA0OsCmuZPVEjyinHrXDo_8BOZaRVdPnxG_MZ2XqOIKNFa4gaxBU&usqp=CAU'})
ref.update({'Explanation' : ''})
ref = db.reference('Product1/양도내역')
y = Yield('2022-05-12', 'sMV3KQmqPmVVtE5qEUKNsxISwTu2')
yieldDate = y.getDate()
ref.update({'Date' : yieldDate})
yieldUID = y.getUID()
ref.update({'UID' : yieldUID})
