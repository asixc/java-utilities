package dev.jotxee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jotxee.images.checkformats.DiscardImagesByFormat;
import dev.jotxee.images.checkformats.DiscardImagesByFormat2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.lang.String.format;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws JsonProcessingException {
       // cleanUpAlreadyRegisteredEans();
        testingDiscardImagesByFormat();
    }

    private static void cleanUpAlreadyRegisteredEans() throws JsonProcessingException {
        String json = "{\"clave1\":\"valor1\",\"clave2\":\"valor2\",\"clave3\":\"valor3\"}";
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, String> registros = objectMapper.readValue(json, Map.class);
        var yaregistrados = List.of("8445922862701", "8445922862718", "8445922862725", "8445922681333", "8445922681340", "8445922682040", "8445922682057", "8445922681357", "8445922681364", "8445922681371", "8445922738884", "8445922739690", "8445922739713", "8445922738907", "8445922738921", "8445922738891", "8445922739706", "8445922739720", "8445922738914", "8445922738938", "8445922938215", "8445922938239", "8445922938253", "8445922938277", "8445922938291", "8445922938314", "8445922938338", "8445922938222", "8445922938246", "8445922938260", "8445922938284", "8445922938307", "8445922938321", "8445922938345", "8445922948733", "8445922948498", "8445922948504", "8445922948511", "8445922948528", "8445922948535", "8445922948542", "8445922910365", "8445922910372", "8445922910389", "8445922910211", "8445922910228", "8445922910235", "8445922910242", "8445922910259", "8445922910167", "8445922910174", "8445922910181", "8445922910198", "8445922910204", "8445922948559", "8445922948566", "8445922948573", "8445922948580", "8445922948597", "8445922948603", "8445922948610", "8445922917098", "8445922917104", "8445922917111", "8445922917128", "8445922917135", "8445922917142", "8445922917159", "8445922846633", "8445922846640", "8445922846657", "8445922846664", "8445922846671", "8445922846688", "8445922846695", "8445922910266", "8445922910273", "8445922910280", "8445922910297", "8445922910303", "8445922949174", "8445922949181", "8445922949365", "8445922949198", "8445922949204", "8445922949211", "8445922949228", "8445922949235", "8445922909581", "8445922909604", "8445922909628", "8445922909642", "8445922909666", "8445922909680", "8445922909703", "8445922909727", "8445922909741", "8445922909765", "8445922909789", "8445922909598", "8445922909611", "8445922909635", "8445922909659", "8445922909673", "8445922909697", "8445922909710", "8445922909734", "8445922909758", "8445922909772", "8445922909796", "8445922847142", "8445922847159", "8445922847166", "8445922847173", "8445922847180", "8445922847197", "8445922847203");
        logger.info(format("[%s] ", registros.size()));
        for (String clave : yaregistrados) {
            registros.remove(clave);
        }
        for (Map.Entry<String, String> entrada : registros.entrySet()) {
            System.out.println(" '" + entrada.getKey() + "','" + entrada.getValue() + "'");
        }
        logger.info(format("[%s] ", registros.size()));
    }

    private static void testingDiscardImagesByFormat() {
        final List<String> imageUrls = List.of(
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57014017_02.jpg?ts=1688711599272&imwidth=457&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/outfit/S20/57014017_02-99999999_01.jpg?ts=1688711599272&imwidth=457&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57014017_02_D1.jpg?ts=1688711599272&imwidth=457&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57014017_02_B.jpg?ts=1686724377172&imwidth=457&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57014017_02_D8.jpg?ts=1686724377172&imwidth=224&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57014017_02_D9.jpg?ts=1686659595039&imwidth=224&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57014017_02_R.jpg?ts=1686724377172&imwidth=224&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57014017_02_D0.jpg?ts=1686724377172&imwidth=224&imdensity=2",

                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57061169_02_D6.jpg?ts=1688031216931&imwidth=457&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57061169_02_B.jpg?ts=1685082922795&imwidth=457&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57061169_02_D8.jpg?ts=1685082922795&imwidth=302&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57061169_02_R.jpg?ts=1685082922795&imwidth=302&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57061169_02_D0.jpg?ts=1685082922795&imwidth=302&imdensity=2",

                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57013267_02_B.jpg?ts=1684998268346&imwidth=457&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57013267_02_D8.jpg?ts=1684998268346&imwidth=457&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57013267_02_D9.jpg?ts=1684933446063&imwidth=302&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57013267_02_R.jpg?ts=1684998268346&imwidth=302&imdensity=2",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57013267_02_D0.jpg?ts=1684998268346&imwidth=302&imdensity=2",

                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57044770_02_B.jpg?ts=1688987019412&imwidth=457&imdensity=2&impolicy=featured",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57044770_02_D8.jpg?ts=1688987019412&imwidth=457&imdensity=2&impolicy=featured",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57044770_02_D9.jpg?ts=1688985198456&imwidth=302&imdensity=2&impolicy=featured",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57044770_02_R.jpg?ts=1688987019412&imwidth=302&imdensity=2&impolicy=featured",
                "https://st.mngbcn.com/rcs/pics/static/T5/fotos/S20/57044770_02_D0.jpg?ts=1688987019412&imwidth=302&imdensity=2&impolicy=featured"
        );


        DiscardImagesByFormat2.getValidImageUrls(imageUrls);//.forEach(logger::info);
    }
}