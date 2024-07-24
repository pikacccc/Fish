 public class G
 {
   public static final int SCREEN_WIDTH = 646;
   public static final int SCREEN_HEIGHT = 530;
   public static final int ORIGINAL_SCREEN_WIDTH = 176;
   public static final int ORIGINAL_SCREEN_HEIGHT = 220;
   public static final float SCREEN_RATE_X = 3.670455F;
   public static final float SCREEN_RATE_Y = 2.409091F;
   public static final int MENU_X = 470;
   public static final int MENU_Y = 530;
   public static final int CP_X = 165;
   public static final int CP_Y = 134;
   public static final int MAIN_MENU_HEIGHT = 29;
   public static final int BACK_WIDTH = 464;
   public static final int BACK_HEIGHT = 140;
   public static final int SCORE_PADDING = 10;
   public static final int AD_X = 478;
   public static int AD_LEVEL = 0;
   public static float AD_RATE = 0.0F;
   public static float AD_RATE_APPEND = 0.2F;
 
   public static int WHITE_PERL_SCORE = 60;
   public static int OTHER = 16;
 
   public static int MAX_PERL_RDM = 100;
 
   public static int NULL_PERL_RDM = 80;
 
   public static int BLACK_PERL_RDM = 90;
 
  public static final int MAIN_MENU_TOP = computeY(170);
 
   public static final int computeX(int x)
   {
     return (int)(x * 3.670455F);
   }
 
   public static final int computeY(int y) {
	   //System.out.println("y * 2.409091F ="+(y * 2.409091F));
	   //System.out.println("yyyy ="+y);
	   
	   
	   //int kmmm = (int)(170 * 5.409091F);
	   //System.out.println("kmmm ="+kmmm);
	   
	   
	   int kkkk = (int)(y * 2.409091F);
	   //System.out.println("kkkk ="+kkkk);
     return kkkk;
   }
 }
