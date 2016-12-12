Инструкция по разворачиванию Montao
===================

#### Комплекс ПО
Чтобы начать развёртывание на машине должен быть установленн следующий комплекс программного обеспечения :
  - PostgreSQL  https://www.postgresql.org/download/
  - JDK http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html
  - TomCat https://tomcat.apache.org/download-90.cgi 
  - IDEA Ultimate
  - Новая версия maven https://maven.apache.org/download.cgi , также нужно указать
  ИДЕЕ, где лежит распакованный мавен - открыть вкладку files->structure найти maven и указать директорию ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/maven%20directory.png) затем нужно добавить на строку Auto-Import в выпадающем окошке в ИДЕЕ ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/maven%20need%20to%20be%20imported.png) и подождать какое-то время.
  - Последняя версия GIT https://git-scm.com/download/win, также ИДЕЯ должна видеть исполняемый файл гита это делается через settings, в поисковике вбивается git и дальше как на скриншоте ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/git%20exe%20position.png)
  - После всех настроек хорошо бы перезагрузить компьютер. Не думаю что это обязательно , но всё же я бы рекомендовал.
  
#### Настройка ПО
В PostgreSQL должна быть создана роль root с паролем root и со всеми возможными привелегиями ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/Add%20new%20role.png)  Также должна быть создана БД  montao с владельцем root ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/add%20new%20database.png) 

#### Развёртывание 
  - Качаем Montao https://github.com/truesik/Montao.git
  - Добавляем созданную базу данных 
  ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/add%20database.png)
  - Настраиваем добавленную базу данных, прописываем имя БД , пользователя и пароль к нему ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/Customize%20database.png)
  - Необходимо проверить свойства гибернейта и заменить старую БД projectstack на montao ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/Edit%20hibernate%20properties.png)
  - Открываем окошко редактирования конфигураций ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/Edit%20configuration.png) и добавляем нужный артефакт ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/Add%20artefact.png), указываем на директорию, где лежит распакованный tomcat ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/TomCat%20folder%20position.png). 
  На той же вкладке выставляем значение "Redeploy" для опции "On Update" ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/On%20Update%20-%20Redeploy.png) и значение "Update resources" для опции "On frame deactivation" ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/On%20frame%20deactivation%20-%20update%20resources.png) Жмём везде "окей" , где только можно.
  - Добавляем новую конфигурацию , для этого заного открываем окошко редактирования конфигураций (см предыдущий пункт) и выбираем "Добавить новую ТомКотовскую конфигурацию" ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/Add%20new%20TomCat%20cofiguration.png) в появившемся окошке задайте благозвучное для вашего слуха имя Сервера ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/TomCat%20name.png)
  - Теперь во вкладке maven projects в выпадающем меню lifecycle нажимаем два раза на "clean" и ждём удачного завершенеия чистки ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/Clean.png) 
  затем из того же меню выбираем "install"  и ждём ![N|Solid](http://filmsforstudy.esy.es/Stack/images/Montao%20tutorial/Install.png)
  - И последний штрих **ВНИМАНИЕ: tomcat должен быть не запущен** - нажимаем "run" (кто не помнит , это зелёный прямоугольник , для компиляции и запуска приложенияя написанного в IDE IDEA)по завершению этой процедуры вас должно перекинуть на открытое приложение в браузеое. **Внимание это происходит только один раз поэтому в следующие разы не поленитесь и после рестарта сервера или редеплоя - заходите в браузер сами.**
