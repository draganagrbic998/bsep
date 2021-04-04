# Пројекат из предмета Безбедност у системима електронског пословања

Чланови тима:
1. Драгана Грбић SW22/2017
2. Харис Гегић SW70/2017
3. Петар Николић SW31/2017

# Ako hocete da iskljucite truststorove i sve vezano za njih:
1. Uzmite za hospital-bekend, hospital-device i simulator najobicnije self signed sertifikate (bilo koji od pre mog komita za truststorovi bi trebao da radi).
2. U AppConfig od super admina, bolnice i uredjaja nadjite loadTrustMaterial i prvi parametar prepravite na null.
3. U application properties od bolnice i uredjaja zakomentarisite sve sto pocinje sa server.ssl OSIM:
- server.ssl.key-store=classpath:keystore.jks
- server.ssl.key-store-password=${KEY_STORE_PASSWORD}
