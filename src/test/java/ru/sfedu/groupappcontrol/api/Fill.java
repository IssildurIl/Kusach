package ru.sfedu.groupappcontrol.api;

import ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion;

public class Fill {
    int maxGen = 100;
    int genId;

    static String[] firstName = {"Ivan", "Kirill", "Sergey", "Daniil", "Denis", "Dmitry", "Yevgeny", "Anatoliy", "Anton", "Vladimir"};
    static String[] lastName = {"Parfenov", "Medvedev", "Yeremin", "Raykin", "Zhirnov", "Shalyapin", "Shchitov", "Kuznetsov", "Tsvetaeva", "Hasymov"};
    static String[] login = {"employee1", "employee2", "employee3", "employee4", "employee5", "employee5", "employee6", "employee7", "employee8", "employee9"};
    static String[] password = {"123admin", "1234nimda", "12345", "Raspbery", "31415", "51220", "311220", "@g004", "password", "theif"};
    static String[] email = {"Parfenov@mail.com", "Medvedev@mail.com", "Yeremin@mail.com", "Raykin@mail.com", "Zhirnov@mail.com", "Shalyapin@mail.com", "Shchitov@mail.com", "Kuznetsov@mail.com", "Tsvetaeva@mail.com", "Hasymov@mail.com"};
    static String[] token = {"e6368342-ae1d-40ef-9bfd-e28eba43aa90", "9bba8047-f0aa-473d-aef9-6905edcd3f99", "56b52b03-d76f-4105-8c06-3a3efb12633a", "e279d5e5-94be-4984-920b-625f92422865", "2e61fe24-15b5-458f-aaac-2e8b4c80218f", "6a169881-15fe-4153-8aad-fdff28fc60d8", "8eff243f-46cb-4532-8b09-dabc5662d0b6", "0a644ce4-1de9-4e22-a867-072bf4c3b780", "68075f8d-04ef-472a-8cdc-6b896d9ee9da", "960651c6-4aa4-4274-9752-c5244a139005"};
    static String[] department = {"Team1", "Team2", "Team3", "Team4", "Team5", "Team6", "Team7", "Team8", "Team9", "Team10"};
    //
    static String[] taskDescription = {"MakeDiploma","MakeKursach","MakeWebSite","MakeApplication","MakeGame","MakeLab","MakeDisign","MakeSmth","MakeOurTask","MakeNewCycle"};
    static Double[] money = {1000.0,2000.0,3000.0,4000.0,5000.0,6000.0,7000.0,8000.0,9000.0,10000.0};
    static String[] createdDate={"04-12-2020","05-12-2021","15-12-2020","15-12-2021","30-12-2020","31-12-2020","17-12-2020","17-12-2020","14-08-2020","09-01-2021"};
    static String[] deadline={"04-12-2022","05-12-2022","15-12-2022","15-12-2022","30-12-2022","31-12-2022","17-12-2022","17-12-2022","14-08-2022","09-01-2022"};
    static String[] lastUpdate={"04-12-2020","04-12-2020","04-12-2020","04-12-2020","04-12-2020","04-12-2020","04-12-2020","04-12-2020","04-12-2020","04-12-2020"};

    static String[] title ={"Game Project","Labs Project","Second Project","Partners Project","WebSite project","Design Project","Diploma Project","MakeOurTask Project","Application Project","SMTH Project"};
}
