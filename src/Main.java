import unisoft.ws.FNSNDSCAWS2;
import unisoft.ws.FNSNDSCAWS2Port;
import unisoft.ws.fnsndscaws2.request.NdsRequest2;
import unisoft.ws.fnsndscaws2.response.NdsResponse2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inn = "";

        while (true){
            System.out.println("Введите ИНН контрагента");
            try {
                inn = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!inn.equals("")) {
                break;
            }
        }

        FNSNDSCAWS2 fnsndscaws2 = new FNSNDSCAWS2();
        FNSNDSCAWS2Port fnsndscaws2Port = fnsndscaws2.getFNSNDSCAWS2Port();

        NdsRequest2 ndsRequest =new NdsRequest2();
        List<NdsRequest2.NP> npList = ndsRequest.getNP();

        NdsRequest2.NP np = new NdsRequest2.NP();
        np.setINN(inn);

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        np.setDT(format.format(new Date()));

        npList.add(np);

        NdsResponse2 response = fnsndscaws2Port.ndsRequest2(ndsRequest);

        String stateString = response.getNP().get(0).getState();
        int state = Integer.parseInt(stateString);
        System.out.println(state);
        switch (state) {
            case 0:
                System.out.println("Налогоплательщик зарегистрирован в ЕГРН и имел статус действующего в указанную дату");
                break;
            case 1:
                System.out.println("Налогоплательщик зарегистрирован в ЕГРН, но не имел статус действующего в указанную дату");
                break;
            case 2:
                System.out.println("Налогоплательщик зарегистрирован в ЕГРН");
                break;
            case 3:
                System.out.println("Налогоплательщик с указанным ИНН зарегистрирован в ЕГРН, КПП не соответствует ИНН или не указан");
                break;
            case 4:
                System.out.println("Налогоплательщик с указанным ИНН не зарегистрирован в ЕГРН");
                break;
            case 5:
                System.out.println("Некорректный ИНН");
                break;
            case 6:
                System.out.println("Недопустимое количество символов ИНН");
                break;
            case 7:
                System.out.println("Недопустимое количество символов КПП");
                break;
            case 8:
                System.out.println("Недопустимые символы в ИНН");
                break;
            case 9:
                System.out.println("Недопустимые символы в ИНН");
                break;
            case 10:
                System.out.println("КПП не должен использоваться при проверке ИП");
                break;
            case 11:
                System.out.println("некорректный формат даты");
                break;
            case 12:
                System.out.println("некорректная дата (ранее 01.01.1991 или позднее текущей даты)");
                break;
        }

        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
