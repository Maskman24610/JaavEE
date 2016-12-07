# JaavEE
JavaEE Homework

第一個作業:將12/04上的範例MVC化
M:建立一個class名為 dbcontrol 於constructor中Connection database，將對資料庫的學生資料表存取、增加新資料、刪除資料、修改資料內容
寫成四個 methad 供control取用
V:寫了三個網頁，為基本觀看的頁面、增加新資料到學生資料表的頁面、還有一個edit頁面，不過最後這個網頁捨棄不用。
C:主要是由一個servlet control構成 有一個用來顯示網頁的outHtml method new 一個 dbcontrol 藉此達到對資料庫的一些操作一些邏輯判斷，藉以顯示出想要的結果。 將顯示修改畫面轉交由另一個servlet edit 來呈現 當按下修改畫面或新增畫面的送出鈕時，將form內容送給control，在由control呼叫dbcontrol中的method來做。
