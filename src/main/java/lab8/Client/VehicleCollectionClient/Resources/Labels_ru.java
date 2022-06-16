package lab8.Client.VehicleCollectionClient.Resources;

import java.util.ListResourceBundle;

public class Labels_ru extends ListResourceBundle {
    public Object[][] getContents() {return contents;}
    private Object[][] contents = {
            {"menuFile", "Файл"},
            {"menuCollection", "Коллекция"},
            {"menuHelp", "Помощь"},
            {"menuFileChangeLang", "Сменить язык"},
            {"menuFileExit", "Закрыть"},
            {"menuServer", "Сервер"},
            {"menuServerConnect", "Подключиться"},
            {"menuServerLogIn", "Войти"},
            {"menuServerRegister", "Зарегестрироваться"},
            {"menuCollectionInfo", "Информация"},
            {"menuCollectionAdd", "Добавить транспорт"},
            {"menuCollectionSort", "Сортировка"},
            {"menuCollectionSortByType", "По типу"},
            {"menuCollectionSortByName", "По имени"},
            {"menuHelpAbout", "О приложении"},

            {"connectionStatusLabel", "Статус подключения"},
            {"collectionInfoLabel", "Информация о коллекции"},

            {"emptyTable", "В коллекции нет транспортных средств"},
            {"idColumn", "ID"},
            {"keyColumn", "Ключ"},
            {"userColumn", "Пользователь"},
            {"nameColumn", "Имя"},
            {"coordinatesColumn", "Координаты"},
            {"coordinatesXColumn", "X"},
            {"coordinatesYColumn", "Y"},
            {"dateColumn", "Дата"},
            {"enginePowerColumn", "Мощность"},
            {"numberOfWheelsColumn", "Количество колёс"},
            {"capacityColumn", "Вместимость"},
            {"typeColumn", "Тип"},
            {"addVehicleButton", "Добавить транспорт"},
            {"attention", "Внимание"},
            {"closeApp", "Закрыть приложение"},

            {"connectToServerWindow", "Подключиться к серверу"},
            {"enterIpAndPort", "Введите IP и порт сервера"},
            {"cancelButton", "Отмена"},
            {"connectButton", "Подключиться"},
            {"unableToConnect", "Не удалось подключиться, попробуйте снова"},
            {"connected", "подключен к серверу"},
            {"notConnected", "не подключен"}};
}
