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


ref = db.reference('Owners/Owner1')
ref.update({'UID' : 'sMV3KQmqPmVVtE5qEUKNsxISwTu2'})
ref.update({'Name' : '김현빈'})
ref = db.reference('Owners/Owner1/ProductList')
ref.update({'ProductID1':'Product1'})
ref.update({'ProductID2':'Product3'})

ref = db.reference('Owners/Owner2')
ref.update({'UID' : 'p2Oo3Sv6QFVh5wHYIMXpP4ZUAGg2'})
ref.update({'Name' : '김길동'})
ref = db.reference('Owners/Owner2/ProductList')
ref.update({'ProductID1':'Product2'})
ref.update({'ProductID2':'Product4'})
