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
        String inn = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            inn = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
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

        System.out.println(response.getNP().get(0).getState());
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
