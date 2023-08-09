package dev.jotxee;

import dev.jotxee.images.checkformats.DiscardImagesByFormat;

import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
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

        DiscardImagesByFormat.getValidImageUrls(imageUrls).forEach(logger::info);
    }
}