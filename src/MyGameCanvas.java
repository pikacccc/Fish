import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import java.io.IOException;
import java.util.Random;

public class MyGameCanvas extends GameCanvas implements Runnable {
    private static MyGameCanvas gc;
    private Thread thdGame;
    private Random rdm = new Random();
    public static int gameState = 4;
    public static int menuIndex = 0;
    public static boolean resumeFlag = false;
    public static boolean pauseFlag;
    public static int helpY = 76;
    private Font font;
    private boolean isRunning = true;
    private boolean keyBack;
    private boolean keyZero;
    private boolean keyOne;
    private boolean keyUp;
    private boolean keyDown;
    private boolean keyLeft;
    private boolean keyRight;
    private boolean keyFire;
    private int screenWidth = 646;
    private int screenHeight = 530;
    private int counter;
    private int level = 1;
    private int lineSpace = 16;
    private Image imgTitle;
    private Image imgPlayerSmall;
    private Image imgPlayerMiddle;
    private Image imgPlayerLarge;
    private Image imgEnemy0Small;
    private Image imgEnemy0Middle;
    private Image imgEnemy0Large;
    private Image imgScores;
    private Image imgTop;
    private Image imgIntro;
    private Image imgYummy;
    private Image imgGrowth;
    private Image imgSharks;
    private Image[] imgUnderWaterWorld = new Image[3];
    private Image[] imgPS = new Image[11];
    private Image[] imgPM = new Image[11];
    private Image[] imgPL = new Image[11];
    private Image[] imgPlayer = new Image[33];
    private Image[] imgEnemy = new Image[167];
    private Image[] imgScore = new Image[13];
    private Image[] imgConch = new Image[4];
    private Image[] imgBubble = new Image[5];
    private Image[] imgPrecious = new Image[5];
    private Image[] imgShark = new Image[4];
    private Image imgNumber;
    private Image imgBy;
    private Image imgShuimu;
    private Image imgMenu;
    private Image imgStun;
    private Image imgArrow;
    private Image imgBackTip;
    private SP[] sprite = new SP[70];
    private SP player;
    private int score;
    private int hit = 2;
    private int collected;
    private int levelCompleteCtr;
    private int yummyCtr = 50;
    private int newCtr = 50;
    private int offsetX;
    private int offsetY;
    private int[] offsetYMax = {16, 16, 16, 24, 24, 24, 32, 32, 32};
    private int[] offsetXMax = {24, 24, 24, 32, 32, 32, 40, 40, 40};
    private int[] offsetXMin = {-24, -24, -24, -32, -32, -32, -40, -40, -40};
    private int smallEaten;
    private int middleEaten;
    private int largeEaten;
    private int smallCtr;
    private int middleCtr;
    private int largeCtr;
    private int freezeCtr;
    private int speedCtr;
    private int doubleCtr;
    private int sharkCtr;
    private int congratulationsCtr;
    private int deadCtr;
    private int gameOverCtr;
    private int rest = 2;
    private int[] small = {2, 3, 4, 5, 6, 7, 8, 9, 10};
    private int[] middle = {3, 4, 5, 6, 7, 8, 9, 10, 11};
    private int[] large = {4, 5, 6, 7, 8, 9, 10, 11, 12};
    private int[] smallCount = {8, 7, 6, 6, 5, 5, 5, 4, 3};
    private int[] middleCount = {2, 3, 4, 5, 5, 5, 6, 7, 8};
    private int[] largeCount = {1, 2, 2, 3, 3, 3, 4, 5, 6};
    private int[] shark = {0, 0, 1, 0, 0, 1, 1, 2, 3};
    private int[] shuimu = {0, 0, 0, 0, 1, 1, 0, 1};
    private int[] goal = {500, 600, 700, 900, 1100, 1300, 1600, 1900, 2200};

    private Graphics g;

    public static synchronized MyGameCanvas getInstance() {
        if (gc == null) {
            gc = new MyGameCanvas();
        }
        return gc;
    }

    private MyGameCanvas() {
        super(false);
        setFullScreenMode(true);
        g = getGraphics();
        this.font = Font.getFont(0, 0, 0);
        if (this.font.getHeight() > 18) {
            this.lineSpace = 20;
        }

        try {
            this.imgTitle = Image.createImage("/res/title.png");
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }

        for (int i = 0; i < this.sprite.length; i++) {
            this.sprite[i] = new SP();
        }

        initPlayer();//modify by jakmax

        addEnemy();//modify by jakmax

        try {
            for (int i = 0; i < this.imgUnderWaterWorld.length; i++) {
                this.imgUnderWaterWorld[i] = Image.createImage("/res/underwaterworld" + i + ".png");
            }

            this.imgPlayerSmall = Image.createImage("/res/player.png");
            for (int i = 0; i < this.imgPS.length; i++) {
                this.imgPS[i] = Image.createImage(this.imgPlayerSmall, i * 37,
                        0, 37, 28, 0);
                this.imgPlayer[i] = this.imgPS[i];
            }

            this.imgPlayerMiddle = Image.createImage("/res/player.png");
            for (int i = 0; i < this.imgPM.length; i++) {
                this.imgPM[i] = Image.createImage(this.imgPlayerMiddle, i * 37,
                        0, 37, 28, 0);
                this.imgPlayer[(11 + i)] = this.imgPM[i];
            }

            this.imgPlayerLarge = Image.createImage("/res/player.png");
            for (int i = 0; i < this.imgPL.length; i++) {
                this.imgPL[i] = Image.createImage(this.imgPlayerLarge, i * 37,
                        0, 37, 28, 0);
                this.imgPlayer[(22 + i)] = this.imgPL[i];
            }
            //System.out.println("===================="+getTimeNow(firstflag)+" <008>=================");
            for (int j = 0; j < 11; j++) {
                this.imgEnemy0Small = Image.createImage("/res/fish" + j + "l.png");
                for (int i = 0; i < 5; i++) {
                    this.imgEnemy[(i + 15 * j)] = Image.createImage(
                            this.imgEnemy0Small, i * 24, 0, 24, 20, 0);
                }

                this.imgEnemy0Middle = Image.createImage("/res/fish" + j + "l.png");
                for (int i = 0; i < 5; i++) {
                    this.imgEnemy[(5 + i + 15 * j)] = Image.createImage(
                            this.imgEnemy0Middle, i * 24, 0, 24, 20, 0);
                }

                this.imgEnemy0Large = Image.createImage("/res/fish" + j + "l.png");
                for (int i = 0; i < 5; i++) {
                    this.imgEnemy[(10 + i + 15 * j)] = Image.createImage(
                            this.imgEnemy0Large, i * 24, 0, 24, 20, 0);
                }
            }

            this.imgScores = Image.createImage("/res/score.png");
            for (int i = 0; i < 13; i++) {
                this.imgScore[i] = Image.createImage(this.imgScores, i * 10, 0,
                        10, 17, 0);
            }

            this.imgConch[0] = Image.createImage("/res/conch0.png");
            this.imgConch[1] = Image.createImage("/res/conch1.png");
            this.imgConch[2] = Image.createImage("/res/conch2.png");
            this.imgConch[3] = Image.createImage("/res/conch3.png");
            this.imgTop = Image.createImage("/res/up.png");

            Image imgBubbles = Image.createImage("/res/bubble.png");
            for (int i = 0; i < this.imgBubble.length; i++) {
                this.imgBubble[i] = Image.createImage(imgBubbles, i * 15, 0,
                        16, 16, 0);
            }

            this.imgIntro = Image.createImage("/res/back.png");
            this.imgYummy = Image.createImage("/res/yummy.png");
            for (int i = 0; i < this.imgPrecious.length; i++) {
                this.imgPrecious[i] = Image.createImage("/res/precious" + i + ".png");
            }

            this.imgGrowth = Image.createImage("/res/exp.png");
            this.imgSharks = Image.createImage("/res/shark.png");
            for (int i = 0; i < this.imgShark.length; i++) {
                this.imgShark[i] = Image.createImage(this.imgSharks, i * 90, 0,
                        90, 32, 0);
            }

            this.imgNumber = Image.createImage("/res/number.png");
            this.imgBy = Image.createImage("/res/mul.png");
            this.imgShuimu = Image.createImage("/res/jellyfish.png");
            this.imgMenu = Image.createImage("/res/menu.png");
            this.imgStun = Image.createImage("/res/dizziness.png");
            this.imgArrow = Image.createImage("/res/arrow.png");
            this.imgBackTip = Image.createImage("/res/backtxt.png");
        } catch (IOException localIOException1) {
            localIOException1.printStackTrace();
        }

        this.thdGame = new Thread(this);
        this.thdGame.start();
    }

    private void newGame() {
        cleanSprites();

        this.level = 1;
        this.hit = 2;
        this.rest = 2;
        this.newCtr = 50;
        this.yummyCtr = 50;
        this.score = 0;
        this.smallEaten = 0;
        this.middleEaten = 0;
        this.largeEaten = 0;
        this.smallCtr = 0;
        this.middleCtr = 0;
        this.largeCtr = 0;
        this.freezeCtr = 0;
        this.speedCtr = 0;
        this.doubleCtr = 0;
        this.sharkCtr = 0;
        this.congratulationsCtr = 0;
        this.deadCtr = 0;
        this.gameOverCtr = 0;
        this.collected = 0;
        G.AD_LEVEL = 0;

        initPlayer();
        addEnemy();
    }

    private void cleanSprites() {
        for (int i = 0; i < this.sprite.length; i++)
            this.sprite[i].type = 0;
    }


    public void DrawAll() {
        g.setFont(this.font);
        if (gameState == 1) {
            drawIntro(g);
        } else if ((gameState == 0) || (gameState == 2)) {
            g.setClip(0, 0, 470, this.screenHeight);
            drawBack(g);
            drawSP(g);
            g.setClip(0, 0, this.screenWidth, this.screenHeight);
            drawStatus(g);
            drawRest(g);

            if (gameState == 2) {
                drawLevelComplete(g);
            }

            if (pauseFlag) {
                drawPause(g);
            }

            if (this.deadCtr > 0) {
                drawDead(g);
            }

            if (this.gameOverCtr > 0)
                drawGameOver(g);
        } else if (gameState == 3) {
            drawAnalys(g);
        } else if (gameState == 4) {
            drawTitle(g);
            drawMenu(g);
        } else if (gameState == 6) {
            drawHelp(g);
        } else if (gameState == 7) {
            drawCongratulations(g);
        }
        flushGraphics();
    }

    private void drawTitle(Graphics g) {
        g.drawImage(this.imgTitle, this.screenWidth / 2, this.screenHeight / 2, 3);
    }

    private void drawMenu(Graphics g) {
        g.setClip(0, G.MAIN_MENU_TOP, this.screenWidth, 38);
        if (menuIndex < 3) {
            g.drawImage(this.imgMenu, this.screenWidth / 2, G.MAIN_MENU_TOP - menuIndex * 38, 17);

        } else {
            g.drawImage(this.imgMenu, this.screenWidth / 2, G.MAIN_MENU_TOP - (menuIndex + 2) * 38, 17);
        }


        g.setClip(0, 0, this.screenWidth, this.screenHeight);
        g.drawImage(this.imgArrow, G.computeX(117 + 2 * (this.counter % 8 / 4)), G.computeY(160), 0);
        g.drawRegion(this.imgArrow, 0, 0, this.imgArrow.getWidth(), this.imgArrow.getHeight(), 2, G.computeX(48 - 2 * (this.counter % 8 / 4)), G.computeY(160), 0);
    }

    private void drawHelp(Graphics g) {
        g.setColor(1144235);
        g.fillRect(0, 0, this.screenWidth, this.screenHeight);
        int introX = this.screenWidth / 2 - this.imgIntro.getWidth() / 2;
        int introY = this.screenHeight / 2 - this.imgIntro.getHeight() / 2;
        g.setColor(16777215);

        int helpX = introX;
        helpY = introY - 90;

        g.drawString("本游戏是一款动作类游戏，玩家控制小鱼在屏幕", helpX, helpY + this.lineSpace, 0);
        g.drawString("内游动即可进行游戏。", helpX, helpY + 2 * this.lineSpace, 0);

        helpY -= 4;
        g.drawString("[操作方法]", helpX, helpY + 4 * this.lineSpace, 0);
        g.drawString("上：向上移动。", helpX, helpY + 5 * this.lineSpace, 0);
        g.drawString("左：向左移动。", helpX, helpY + 6 * this.lineSpace, 0);
        g.drawString("右：向右移动。", helpX, helpY + 7 * this.lineSpace, 0);
        g.drawString("下：向下移动。", helpX, helpY + 8 * this.lineSpace, 0);
        g.drawString("数字键0：回到主菜单。", helpX, helpY + 9 * this.lineSpace, 0);
        g.drawString("返回键或Home键：直接退出游戏。", helpX, helpY + 10 * this.lineSpace, 0);

        helpY -= 4;
        g.drawString("[游戏规则]", helpX, helpY + 12 * this.lineSpace, 0);
        g.drawString("吃掉比自己小的鱼可以使自己成长（注意避开比", helpX, helpY + 13
                * this.lineSpace, 0);
        g.drawString("自己大的鱼），成长完全进入下一关。", helpX, helpY + 14
                * this.lineSpace, 0);

        helpY -= 4;
        g.drawString("本界面按确定键回到主菜单。", helpX, helpY + 16 * this.lineSpace, 0);
    }

    private void drawIntro(Graphics g) {
        g.setColor(1144235);
        g.drawImage(this.imgUnderWaterWorld[((this.level - 1) / 3)],
                this.screenWidth / 2, this.screenHeight / 2, 3);
        int introX = this.screenWidth / 2 - this.imgIntro.getWidth() / 2;
        int introY = this.screenHeight / 2 - this.imgIntro.getHeight() / 2;
        g.drawImage(this.imgIntro, introX, introY, 0);
        g.setColor(16777215);
        this.drawString(g,"第" + this.level + "关", this.screenWidth / 2, introY + 28,
                17);
        g.drawString("提示：", introX + 24, introY + 70, 0);
        if (this.level == 1)
            this.drawString(g,"吃掉小鱼使自己成长。", introX + 24, introY + 92, 0);
        else if (this.level == 2)
            this.drawString(g,"2x可在短时间内获得2倍积分。", introX + 24, introY + 92, 0);
        else if (this.level == 3)
            this.drawString(g,"小心鲨鱼，不能碰到它们。", introX + 24, introY + 92, 0);
        else if (this.level == 4)
            this.drawString(g,"进入新的海域。", introX + 24, introY + 92, 0);
        else if (this.level == 5)
            this.drawString(g,"注意水母，碰到会被麻痹一段时间。", introX + 24, introY + 92, 0);
        else if (this.level == 6)
            this.drawString(g,"雪花可以使所有敌人麻痹一段时间。", introX + 24, introY + 92, 0);
        else if (this.level == 7)
            this.drawString(g,"进入新的海域，小心敌人。", introX + 24, introY + 92, 0);
        else if (this.level == 8)
            this.drawString(g,"小心鲨鱼，已经接近胜利了。", introX + 24, introY + 92, 0);
        else if (this.level == 9) {
            this.drawString(g,"注意大白鲨，完成整个游戏吧。", introX + 24, introY + 92, 0);
        }

        this.drawString(g,"确定", introX + 24,
                introY + this.imgIntro.getHeight() - 30, 0);
    }

    private void drawCongratulations(Graphics g) {
        g.setColor(1144235);
        g.fillRect(0, 0, this.screenWidth, this.screenHeight);
        int introX = this.screenWidth / 2 - this.imgIntro.getWidth() / 2;
        int introY = this.screenHeight / 2 - this.imgIntro.getHeight() / 2;
        g.drawImage(this.imgIntro, introX, introY, 0);
        g.setColor(16777215);
        this.drawString(g,"恭喜您完成游戏!", this.screenWidth / 2,
                this.screenHeight / 2 - 8, 17);
    }

    private void drawAnalys(Graphics g) {
        g.setColor(26, 126, 234);
        g.fillRect(0, 0, this.screenWidth, this.screenHeight);
        int introX = this.getWidth() / 2;
        int introY = this.getHeight() / 2;
        g.drawImage(this.imgIntro, introX - this.imgIntro.getWidth() / 2, introY - this.imgIntro.getHeight() / 2, 0);
        g.setColor(0, 0, 0);
        this.drawString(g, "第" + this.level + "关统计", this.screenWidth / 2, introY - 50, 17);
        g.drawImage(this.imgEnemy[((this.small[(this.level - 1)] - 2) * 15)],
                introX - 30, introY - 25, 0);
        g.drawImage(this.imgEnemy[((this.middle[(this.level - 1)] - 2) * 15)],
                introX - 30, introY, 0);
        g.drawImage(this.imgEnemy[((this.large[(this.level - 1)] - 2) * 15)],
                introX - 30, introY + 25, 0);
        g.drawImage(this.imgBy, introX, introY - 20, 0);
        g.drawImage(this.imgBy, introX, introY + 5, 0);
        g.drawImage(this.imgBy, introX, introY + 30, 0);

        int numW = 16;
        int numH = 24;
        g.setClip(introX + 18, introY - 26, numW, numH);
        g.drawImage(this.imgNumber, introX + 18 - 16 * (this.smallEaten / 10),
                introY - 26, 0);
        g.setClip(introX + 34, introY - 26, numW, numH);
        g.drawImage(this.imgNumber, introX + 34 - 16 * (this.smallEaten % 10),
                introY - 26, 0);
        g.setClip(introX + 18, introY, numW, numH);
        g.drawImage(this.imgNumber, introX + 18 - 16 * (this.middleEaten / 10),
                introY, 0);
        g.setClip(introX + 34, introY, numW, numH);
        g.drawImage(this.imgNumber, introX + 34 - 16 * (this.middleEaten % 10),
                introY, 0);
        g.setClip(introX + 18, introY + 25, numW, numH);
        g.drawImage(this.imgNumber, introX + 18 - 16 * (this.largeEaten / 10),
                introY + 25, 0);
        g.setClip(introX + 34, introY + 25, numW, numH);
        g.drawImage(this.imgNumber, introX + 34 - 16 * (this.largeEaten % 10),
                introY + 25, 0);

        g.setClip(0, 0, this.screenWidth, this.screenHeight);
        g.setColor(0, 0 ,0);
        this.drawString(g, "确定", introX + 110, introY + 30, 0);
    }

    private void drawBack(Graphics g) {
        g.drawImage(this.imgUnderWaterWorld[((this.level - 1) / 3)], this.offsetX / 3
                + this.screenWidth / 2, this.offsetY / 3 + this.screenHeight
                / 2, 3);
        if ((this.level - 1) / 3 == 0)
            g.setColor(9394939);
        else if ((this.level - 1) / 3 == 1)
            g.setColor(3741128);
        else if ((this.level - 1) / 3 == 2) {
            g.setColor(2162853);
        }
    }

    private void drawSP(Graphics g) {
        for (int i = 0; i < this.sprite.length; i++)
            if (this.sprite[i].type != 0) {
                SP sp = this.sprite[i];

                if (sp.type == 1) {
                    if (this.deadCtr > 0) {
                        continue;
                    }
                    if (this.gameOverCtr > 0) {
                        continue;
                    }
                    if (this.yummyCtr > 0) {
                        g.drawImage(this.imgYummy, this.player.x
                                + this.player.width, this.player.y
                                - this.imgYummy.getHeight(), 0);
                        if (this.player.level == 0)
                            g.drawImage(
                                    this.imgEnemy[((this.small[(this.level - 1)] - 2) * 15)],
                                    this.player.x + this.player.width
                                            + 10, this.player.y
                                            - this.imgYummy.getHeight()
                                            + 10, 0);
                        else if (this.player.level == 1)
                            g.drawImage(
                                    this.imgEnemy[((this.middle[(this.level - 1)] - 2) * 15)],
                                    this.player.x + this.player.width
                                            + 10, this.player.y
                                            - this.imgYummy.getHeight()
                                            + 10, 0);
                        else if (this.player.level == 2) {
                            g.drawImage(
                                    this.imgEnemy[((this.large[(this.level - 1)] - 2) * 15)],
                                    this.player.x + this.player.width
                                            + 10, this.player.y
                                            - this.imgYummy.getHeight()
                                            + 10, 0);
                        }
                    }

                    if ((this.newCtr > 0) && (this.newCtr % 6 / 3 == 0)) {
                        continue;
                    }

                    if (this.player.action == 0) {
                        if (this.player.direction == 0)
                            g.drawImage(
                                    this.imgPlayer[(this.player.level * 11 + this.counter % 9 / 3)],
                                    this.player.x, this.player.y, 0);
                        else {
                            g.drawImage(
                                    this.imgPlayer[(this.player.level * 11 + 3 + this.counter % 9 / 3)],
                                    this.player.x, this.player.y, 0);
                        }
                    } else if (this.player.action == 1) {
                        if ((this.player.ctr > 6) || (this.player.ctr < 3)) {
                            if (this.player.direction == 0)
                                g.drawImage(
                                        this.imgPlayer[(this.player.level * 11 + this.counter % 9 / 3)],
                                        this.player.x, this.player.y, 0);
                            else
                                g.drawImage(
                                        this.imgPlayer[(this.player.level * 11 + 3 + this.counter % 9 / 3)],
                                        this.player.x, this.player.y, 0);
                        } else {
                            g.drawImage(
                                    this.imgPlayer[(this.player.level * 11 + 6)],
                                    this.player.x, this.player.y, 0);
                        }
                    } else if (this.player.action == 13) {
                        g.drawImage(
                                this.imgPlayer[(this.player.level * 11
                                        + 7 + this.player.direction * 2 + this.player.ctr % 6 / 3)],
                                this.player.x, this.player.y, 0);
                    } else if (this.player.action == 15) {
                        g.drawImage(
                                this.imgPlayer[(this.player.level * 11 + this.player.direction * 3)],
                                this.player.x, this.player.y, 0);
                        g.setClip(sp.x + sp.width / 2 - 6, sp.y - 6, 12, 6);
                        g.drawImage(this.imgStun, sp.x + sp.width / 2 - 6 - 12
                                * (this.counter % 6 / 3), sp.y - 6, 0);
                        g.setClip(0, 0, this.screenWidth, this.screenHeight);
                    }

                } else if (sp.type > 1) {
                    if ((sp.type >= 2) && (sp.type <= 12)) {
                        if ((sp.action == 3) || (sp.action == 4)
                                || (sp.action == 2) || (sp.action == 0)) {
                            if (sp.direction == 0)
                                g.drawImage(
                                        this.imgEnemy[((sp.type - 2)
                                                * 15 + sp.level * 5 + this.counter % 6 / 3)],
                                        sp.x, sp.y, 0);
                            else {
                                g.drawImage(
                                        this.imgEnemy[((sp.type - 2)
                                                * 15 + sp.level * 5 + 2 + this.counter % 6 / 3)],
                                        sp.x, sp.y, 0);
                            }
                        } else if (sp.action == 1) {
                            if ((sp.ctr > 6) || (sp.ctr < 3)) {
                                if (sp.direction == 0)
                                    g.drawImage(
                                            this.imgEnemy[((sp.type - 2)
                                                    * 15 + sp.level * 5 + this.counter % 6 / 3)],
                                            sp.x, sp.y, 0);
                                else
                                    g.drawImage(
                                            this.imgEnemy[((sp.type - 2)
                                                    * 15
                                                    + sp.level
                                                    * 5
                                                    + 2 + this.counter % 6 / 3)],
                                            sp.x, sp.y, 0);
                            } else {
                                g.drawImage(this.imgEnemy[((sp.type - 2) * 15
                                        + sp.level * 5 + 4)], sp.x, sp.y, 0);
                            }
                        }
                    } else if (sp.type == 9999) {
                        if (sp.value / 100 == 0) {
                            g.drawImage(this.imgScore[2], sp.x, sp.y, 0);
                            g.drawImage(this.imgScore[(sp.value / 10 + 3)],
                                    sp.x + 10, sp.y, 0);
                            g.drawImage(this.imgScore[3], sp.x + 20, sp.y, 0);
                        } else {
                            g.drawImage(this.imgScore[2], sp.x, sp.y, 0);
                            g.drawImage(this.imgScore[(sp.value / 100 + 3)],
                                    sp.x + 10, sp.y, 0);
                            g.drawImage(
                                    this.imgScore[(sp.value % 100 / 10 + 3)],
                                    sp.x + 20, sp.y, 0);
                            g.drawImage(this.imgScore[3], sp.x + 30, sp.y, 0);
                        }
                    } else if (sp.type == 9998) {
                        if (sp.action == 6) {
                            g.drawImage(this.imgConch[3], sp.x, sp.y, 0);
                        } else if (sp.action == 8) {
                            g.drawImage(this.imgConch[2], sp.x, sp.y, 0);
                        } else if (sp.action == 7) {
                            if (sp.ctr > 5)
                                g.drawImage(this.imgConch[3], sp.x, sp.y, 0);
                            else
                                g.drawImage(this.imgConch[2], sp.x, sp.y, 0);
                        }
                    } else if ((sp.type == 9996) || (sp.type == 9997)) {
                        g.setClip(sp.x, sp.y, 16, 16);
                        g.drawImage(this.imgBubble[sp.value], sp.x, sp.y, 0);
                        g.setClip(0, 0, this.screenWidth, this.screenHeight);
                    } else if (sp.type == 22) {
                        g.drawImage(this.imgPrecious[0], sp.x, sp.y, 0);
                    } else if (sp.type == 24) {
                        g.drawImage(this.imgPrecious[2], sp.x, sp.y, 0);
                    } else if (sp.type == 23) {
                        g.drawImage(this.imgPrecious[1], sp.x, sp.y, 0);
                    } else if (sp.type == 25) {
                        g.drawImage(this.imgPrecious[3], sp.x, sp.y, 0);
                    } else if (sp.type == 26) {
                        g.drawImage(this.imgPrecious[4], sp.x, sp.y, 0);
                    } else if (sp.type == 9995) {
                        g.setClip(sp.x, sp.y, 28, 35);
                        if (sp.ctr > 18)
                            g.drawImage(this.imgShuimu, sp.x, sp.y, 0);
                        else if (sp.ctr > 16)
                            g.drawImage(this.imgShuimu, sp.x - 28, sp.y, 0);
                        else if (sp.ctr > 14)
                            g.drawImage(this.imgShuimu, sp.x - 56, sp.y, 0);
                        else if (sp.ctr > 7)
                            g.drawImage(this.imgShuimu, sp.x - 84, sp.y, 0);
                        else {
                            g.drawImage(this.imgShuimu, sp.x - 112, sp.y, 0);
                        }
                        g.setClip(0, 0, this.screenWidth, this.screenHeight);
                    } else if ((this.sprite[i].type == 21)
                            || (this.sprite[i].type == 20)) {
                        if (this.sprite[i].type == 21)
                            g.drawImage(this.imgConch[1], this.sprite[i].x,
                                    this.sprite[i].y, 0);
                        else
                            g.drawImage(this.imgConch[0], this.sprite[i].x,
                                    this.sprite[i].y, 0);
                    } else if (sp.type == 27) {
                        g
                                .drawImage(
                                        this.imgShark[(sp.direction * 2 + this.counter % 6 / 3)],
                                        sp.x, sp.y, 0);
                    }
                }
            }
    }

    private void drawStatus(Graphics g) {
        drawBoard(g);
        drawScore(g);
        drawGrowth(g);
        drawCanEat(g);
        drawPrecious(g);
    }

    private int randomBubble() {
        return (this.rdm.nextInt() >>> 1) % 5;
    }

    private void drawBoard(Graphics g) {
        g.setColor(12756732);
        g.drawImage(this.imgTop, 470, 0, 0);
        g.drawImage(imgBackTip,478, 530 - 50, 0);
        g.setColor(12756732);

    }

    private void drawScore(Graphics g) {
        g.drawImage(this.imgScore[(this.score / 1000000 % 10 + 3)], 480, 10, 0);
        g.drawImage(this.imgScore[(this.score / 100000 % 10 + 3)], 490, 10, 0);
        g.drawImage(this.imgScore[(this.score / 10000 % 10 + 3)], 500, 10, 0);
        g.drawImage(this.imgScore[(this.score / 1000 % 10 + 3)], 510, 10, 0);
        g.drawImage(this.imgScore[(this.score / 100 % 10 + 3)], 520, 10, 0);
        g.drawImage(this.imgScore[(this.score / 10 % 10 + 3)], 530, 10, 0);
        g.drawImage(this.imgScore[3], 540, 10, 0);
    }

    private void drawGrowth(Graphics g) {
        g.setColor(0);
        g.drawImage(this.imgGrowth, 480, 28, 0);
        int GH = this.screenWidth - 470 - 60;
        int GX = 520;
        g.fillRect(520, 33, GH, 8);

        g.setColor(15066393);
        if (this.collected * GH / this.goal[(this.level - 1)] < GH)
            g.fillRect(521, 34, this.collected * GH
                    / this.goal[(this.level - 1)], 6);
        else {
            g.fillRect(521, 34, GH, 6);
        }

        g.setColor(12066076);
        g.fillTriangle(520 + (GH - 2) / 3, 40, 516 + (GH - 2) / 3, 45,
                524 + (GH - 2) / 3, 45);
        g.fillTriangle(520 + (GH - 2) * 2 / 3, 40, 516 + (GH - 2) * 2 / 3, 45,
                524 + (GH - 2) * 2 / 3, 45);
    }

    private void drawCanEat(Graphics g) {
        g.drawImage(this.imgEnemy[((this.small[(this.level - 1)] - 2) * 15)],
                480, 60, 0);
        if (this.player.level >= 1) {
            g.drawImage(
                    this.imgEnemy[((this.middle[(this.level - 1)] - 2) * 15)],
                    480, 90, 0);
        }
        if (this.player.level == 2)
            g.drawImage(
                    this.imgEnemy[((this.large[(this.level - 1)] - 2) * 15)],
                    480, 120, 0);
    }

    private void drawRest(Graphics g) {
//        g.drawImage(this.imgPlayer[0], 598, 13, 0);
//        g.drawImage(this.imgBy, 618, 13, 0);
//        g.setClip(625, 10, 6, 13);
//        g.drawImage(this.imgNumber, 625 - this.rest * 6, 13, 0);
//        g.setClip(0, 0, this.screenWidth, this.screenHeight);
    }

    private void drawPrecious(Graphics g) {
        if (this.doubleCtr > 0) {
            g.drawImage(this.imgPrecious[1], this.screenWidth - 25, 50, 0);
        }
        if (this.speedCtr > 0) {
            g.drawImage(this.imgPrecious[3], this.screenWidth - 25, 50, 0);
        }
        if (this.freezeCtr > 0)
            g.drawImage(this.imgPrecious[4], this.screenWidth - 25, 50, 0);
    }

    private void drawLevelComplete(Graphics g) {
        g.setColor(16777215);
        this.drawString(g,"第" + this.level + "关完成", this.screenWidth / 2,
                this.screenHeight / 2 - 8, 17);
    }

    private void drawPause(Graphics g) {
        g.setColor(1144235);
        g.drawImage(this.imgUnderWaterWorld[((this.level - 1) / 3)],
                this.screenWidth / 2, this.screenHeight / 2, 3);
        int introX = this.screenWidth / 2 - this.imgIntro.getWidth() / 2;
        int introY = this.screenHeight / 2 - this.imgIntro.getHeight() / 2;
        g.drawImage(this.imgIntro, introX, introY, 0);
        g.setColor(16777215);
        g.drawString("提示", this.screenWidth / 2, introY + 28, 17);
        g.drawString("您确定要返回主菜单？", introX + 24, introY + 70, 0);
        g.drawString("0-继续游戏，1-返回主菜单。", introX + 24, introY + 102, 0);
    }

    private void drawDead(Graphics g) {
        g.setColor(16777215);
        g.drawString("你挂了！", this.screenWidth / 2, this.screenHeight / 2 - 8,
                17);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(16777215);
        g.drawString("游戏结束！", this.screenWidth / 2, this.screenHeight / 2 - 8,
                17);
    }

    public void keyPressed(int keyCode) {
        this.keyUp = false;
        this.keyDown = false;
        this.keyLeft = false;
        this.keyRight = false;
        this.keyFire = false;
        this.keyBack = false;
        this.keyZero = false;
        this.keyOne = false;
        System.out.println("******** keyPressed keyCode=" + keyCode);
        switch (keyCode) {
            case 49:
                System.out.println("********GAME Source keyPressed this.keyOne=" + keyCode);
                this.keyUp = true;
                this.keyLeft = true;
                this.keyOne = true;
                break;
            case 51:
                this.keyUp = true;
                this.keyRight = true;
                break;
            case 55:
                this.keyDown = true;
                this.keyLeft = true;
                break;
            case 57:
                this.keyDown = true;
                this.keyRight = true;
                break;
            case -1:
            case 50:
                this.keyUp = true;
                break;
            case -2:
            case 56:
                this.keyDown = true;
                break;
            case -3:
            case 52:
                this.keyLeft = true;
                break;
            case -4:
            case 54:
                this.keyRight = true;
                break;
            case -5:
            case 53:
                System.out.println("********GAME Source keyPressed this.keyFire=" + keyCode);
                this.keyFire = true;
                break;
            case 48:
                System.out.println("********GAME Source keyPressed this.keyZero=" + keyCode);
                this.keyZero = true;
                break;
            case -11:
            case -7:
            case -6:
                this.keyBack = true;
        }

        DrawAll();
    }

    public void keyReleased(int keyCode) {
        this.keyLeft = false;
        this.keyRight = false;
        this.keyUp = false;
        this.keyDown = false;
        this.keyFire = false;
    }

    public void run() {
        while (this.isRunning) {
            long startTime = System.currentTimeMillis();
            DrawAll();
            update();
            long tTime = System.currentTimeMillis() - startTime;
            if (tTime >= 30L)
                continue;
            try {
                Thread.sleep( 30L - tTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        MainGame.instance.notifyDestroyed();
    }

    private void update() {
        this.counter += 1;

        if (gameState == 0) {
            if (this.keyBack) {
                this.keyBack = false;
                this.isRunning = false;
            }

            if (this.keyZero) {
                //System.out.println("****************GAME Source update() pauseFlag="+ pauseFlag);
                this.keyZero = false;
                gameState = 4;
//                if (pauseFlag) {
//                    pauseFlag = false;
//                } else {
//                    pauseFlag = true;
//                }

            }

            if ((this.keyOne) && (pauseFlag)) {
                this.keyOne = false;
                gameState = 4;
                menuIndex = 4;
                resumeFlag = true;
            }

            if (!pauseFlag) {
                randomBubbles();
                updatePlayer();
                updateEnemies();
            }

        } else if (gameState == 2) {
            if (this.levelCompleteCtr > 0) {
                this.levelCompleteCtr -= 1;
                updatePlayer();
            } else {
                gameState = 3;
                this.smallCtr = this.smallEaten;
                this.middleCtr = this.middleEaten;
                this.largeCtr = this.largeEaten;
            }
        } else if (gameState == 1) {
            if (this.keyFire) {
                this.keyFire = false;
                this.yummyCtr = 50;
                this.newCtr = 50;
                gameState = 0;
            }
        } else if (gameState == 3) {
            if (this.counter % 3 == 0) {
                if (this.smallCtr > 0)
                    this.smallCtr -= 1;
                else if (this.middleCtr > 0)
                    this.middleCtr -= 1;
                else if (this.largeCtr > 0) {
                    this.largeCtr -= 1;
                }
            }

            if (this.keyFire) {
                this.keyFire = false;
                if (this.level < 9) {
                    this.yummyCtr = 50;
                    this.newCtr = 50;
                    this.level += 1;
                    initLevel(this.level);
                    gameState = 0;
                } else {
                    gameState = 7;
                    this.congratulationsCtr = 100;
                }
            }
        } else if (gameState == 4) {
            updateMenu();
        } else if (gameState == 5) {
            updateAbout();
        } else if (gameState == 6) {
            updateHelp();
        } else if (gameState == 7) {
            if (this.congratulationsCtr > 0) {
                this.congratulationsCtr -= 1;
            } else if (this.congratulationsCtr == 0) {
                gameState = 4;
                resumeFlag = false;
                menuIndex = 0;
            }
        }
    }

    private void updateMenu() {
        //System.out.println("===========  updateMenu");
        if (this.keyFire) {
            this.keyFire = false;
            switch (menuIndex) {
                case 0:
                    gameState = 0;
                    newGame();
                    pauseFlag = false;
                    break;
                case 1:
                    gameState = 6;
                    helpY = 76;
                    break;
                case 2:
                    this.isRunning = false;
                    break;
                default:
                    break;
            }
        } else if (this.keyLeft) {
            this.keyLeft = false;
            if (resumeFlag) {
                menuIndex -= 1;
                menuIndex = (menuIndex + 4) % 4;
            } else {
                menuIndex -= 1;
                menuIndex = (menuIndex + 4 - 1) % 3;
            }
        } else if (this.keyRight) {
            this.keyRight = false;
            if (resumeFlag) {
                menuIndex += 1;
                menuIndex = (menuIndex + 4) % 4;
            } else {
                menuIndex += 1;
                menuIndex = (menuIndex + 4 - 1) % 3;
            }
        }
    }

    private void updateHelp() {
        //System.out.println("===========  updateHelp");
        if (this.keyFire) {
            this.keyFire = false;
            gameState = 4;
        }
    }

    private void updateAbout() {
        //System.out.println("===========  updateAbout");
        if (this.keyFire) {
            this.keyFire = false;
            gameState = 4;
        }
    }

    private void randomBubbles() {
        //System.out.println("===========  randomBubbles");
        int r = (this.rdm.nextInt() >>> 1) % 50;
        if (r == 0) {
            initBubble();
        }
        r = (this.rdm.nextInt() >>> 1) % 200;
        if (r == 0)
            initBubblePrecious();
    }

    private void updatePlayer() {
        //System.out.println("===========  updatePlayer");
        if (this.gameOverCtr > 0) {
            if (this.gameOverCtr == 1) {
                gameState = 4;
                menuIndex = 0;
                resumeFlag = false;
            }
            this.gameOverCtr -= 1;
            return;
        }
        if (this.yummyCtr > 0) {
            this.yummyCtr -= 1;
        }
        if (this.newCtr > 0) {
            this.newCtr -= 1;
        }
        if (this.deadCtr > 0) {
            this.deadCtr -= 1;
        }
        if (this.doubleCtr > 0) {
            this.doubleCtr -= 1;
        }
        if (this.speedCtr > 0) {
            this.speedCtr -= 1;
        }
        if (this.player.ctr > 0) {
            this.player.ctr -= 1;
            if ((this.player.action == 1) && (this.player.ctr == 5)) {
                if (this.player.direction == 0)
                    this.player.direction = 1;
                else {
                    this.player.direction = 0;
                }
            }

            if ((this.player.ctr == 0)
                    && ((this.player.action == 1) || (this.player.action == 13) || (this.player.action == 15))) {
                this.player.action = 0;
            }
        }

        if ((gameState == 0)
                && ((this.player.action == 0) || (this.player.action == 13) || (this.player.action == 1))
                && (this.deadCtr == 0)) {
            int s = 0;
            if (this.speedCtr > 0)
                s = 4;
            else {
                s = 2;
            }

            if (this.keyUp) {
                if (this.player.y > 0 + 1 * s + 32) {
                    this.player.y -= 2 * s;
                } else if (this.offsetY < this.offsetYMax[(this.level - 1)]) {
                    this.offsetY += 2 * s;
                    for (int i = 0; i < this.sprite.length; i++)
                        if (this.sprite[i].type > 1) {
                            SP sp = this.sprite[i];
                            sp.y += 2 * s;
                        }
                } else if (this.player.y > 0 + 1 * s) {
                    this.player.y -= 2 * s;
                }

            }

            if (this.keyDown) {
                if (this.player.y < this.screenHeight - this.player.height - 1
                        * s - 32) {
                    this.player.y += 2 * s;
                } else if (this.offsetY > 0) {
                    this.offsetY -= 2 * s;
                    for (int i = 0; i < this.sprite.length; i++)
                        if (this.sprite[i].type > 1) {
                            SP sp = this.sprite[i];
                            sp.y -= 2 * s;
                        }
                } else if (this.player.y < this.screenHeight
                        - this.player.height - 1 * s) {
                    this.player.y += 2 * s;
                }

            }

            if (this.keyLeft) {
                if (this.player.x > 1 * s + 32) {
                    this.player.x -= 2 * s;
                } else if (this.offsetX < this.offsetXMax[(this.level - 1)]) {
                    this.offsetX += 2 * s;
                    for (int i = 0; i < this.sprite.length; i++)
                        if (this.sprite[i].type > 1) {
                            SP sp = this.sprite[i];
                            sp.x += 2 * s;
                        }
                } else if (this.player.x > 1 * s) {
                    this.player.x -= 2 * s;
                }
                if ((this.player.direction == 0) && (this.player.action != 1)) {
                    this.player.ctr = 9;
                    this.player.action = 1;
                }

            }

            if (this.keyRight) {
                if (this.player.x < this.screenWidth - 176 - this.player.width
                        - 1 * s - 32) {
                    this.player.x += 2 * s;
                } else if (this.offsetX > this.offsetXMin[(this.level - 1)]) {
                    this.offsetX -= 2 * s;
                    for (int i = 0; i < this.sprite.length; i++)
                        if (this.sprite[i].type > 1) {
                            SP sp = this.sprite[i];
                            sp.x -= 2 * s;
                        }
                } else if (this.player.x < this.screenWidth - 176
                        - this.player.width - 1 * s) {
                    this.player.x += 2 * s;
                }
                if ((this.player.direction == 1) && (this.player.action != 1)) {
                    this.player.ctr = 9;
                    this.player.action = 1;
                }
            }
        }
    }

    private void updateEnemies() {
        if (this.freezeCtr > 0) {
            this.freezeCtr -= 1;
        }
        if (this.sharkCtr > 0)
            this.sharkCtr -= 1;
        else if ((this.sharkCtr == 0) && (this.shark[(this.level - 1)] > 0)) {
            randomShark();
        }
        if (this.shuimu[(this.level - 1)] > 0) {
            randomShuimu();
        }
        for (int i = 0; i < this.sprite.length; i++)
            if (this.sprite[i].type > 1) {
                SP enemy = this.sprite[i];

                if (enemy.ctr > 0) {
                    enemy.ctr -= 1;
                    if (enemy.action != 2) {
                        if (enemy.action == 3) {
                            int r = (this.rdm.nextInt() >>> 1) % 10;
                            if (r > 2) {
                                if (enemy.x < this.player.x) {
                                    enemy.x += 1;
                                    if (enemy.direction == 1) {
                                        enemy.ctr = 9;
                                        enemy.action = 1;
                                    }
                                } else if (enemy.x > this.player.x) {
                                    enemy.x -= 1;
                                    if (enemy.direction == 0) {
                                        enemy.ctr = 9;
                                        enemy.action = 1;
                                    }
                                }
                            }
                            if (r < 8) {
                                if (enemy.y < this.player.y)
                                    enemy.y += 1;
                                else if (enemy.y > this.player.y)
                                    enemy.y -= 1;
                            }
                        } else if (enemy.action == 4) {
                            int r = (this.rdm.nextInt() >>> 1) % 10;
                            if (r > 2) {
                                if (enemy.x > this.player.x) {
                                    enemy.x += 2;
                                    if (enemy.direction == 1) {
                                        enemy.ctr = 9;
                                        enemy.action = 1;
                                    }
                                } else if (enemy.x < this.player.x) {
                                    enemy.x -= 2;
                                    if (enemy.direction == 0) {
                                        enemy.ctr = 9;
                                        enemy.action = 1;
                                    }
                                }
                            }
                            if (r < 8) {
                                if (enemy.y > this.player.y)
                                    enemy.y += 2;
                                else if (enemy.y < this.player.y)
                                    enemy.y -= 2;
                            }
                        } else if (enemy.action == 1) {
                            if (enemy.ctr == 5) {
                                if (enemy.direction == 0)
                                    enemy.direction = 1;
                                else
                                    enemy.direction = 0;
                            }
                        } else if (enemy.action == 5) {
                            enemy.y -= 2;
                            if (enemy.y < -this.offsetYMax[(this.level - 1)])
                                enemy.type = 0;
                        } else if (enemy.action == 7) {
                            if ((this.player.x + this.player.width / 2 < enemy.x
                                    + enemy.width)
                                    && (this.player.x + this.player.width / 2 > enemy.x)
                                    && (this.player.y + this.player.height / 2 < enemy.y
                                    + enemy.height)
                                    && (this.player.y + this.player.height / 2 > enemy.y))
                                playerDead();
                        } else if (enemy.action == 14) {
                            enemy.y -= 1;
                            if (enemy.y < -this.offsetYMax[(this.level - 1)])
                                enemy.type = 0;
                        }
                    }
                }
                if (enemy.ctr == 0) {
                    if (enemy.action == 5) {
                        enemy.type = 0;
                    } else if (enemy.action == 6) {
                        for (int j = 0; j < this.sprite.length; j++) {
                            if ((this.sprite[j].type == 21)
                                    || (this.sprite[j].type == 20)) {
                                this.sprite[j].type = 0;
                            }
                        }
                        enemy.action = 7;
                        enemy.ctr = 9;
                    } else if (enemy.action == 7) {
                        enemy.action = 8;
                        enemy.ctr = 25;
                    } else if (enemy.action == 8) {
                        initPrecious(enemy);
                        enemy.action = 6;
                        enemy.ctr = 50;
                    } else if (enemy.action == 10) {
                        int r = (this.rdm.nextInt() >>> 1) % 10;
                        if (r > 3)
                            enemy.y -= 2;
                        else if (r > 1) {
                            enemy.y -= 1;
                        }
                        if (this.counter % 3 == 0) {
                            int s = (this.rdm.nextInt() >>> 1) % 5 - 2;
                            enemy.x += s;
                        }
                        if (enemy.y < -this.offsetYMax[(this.level - 1)])
                            enemy.type = 0;
                    } else if (enemy.action == 11) {
                        enemy.y -= 2;
                        if (enemy.y < -this.offsetYMax[(this.level - 1)])
                            enemy.type = 0;
                    } else if (enemy.action == 12) {
                        enemy.x += 3 - 6 * enemy.direction;
                        if ((enemy.x < this.offsetXMin[(this.level - 1)]
                                - enemy.width)
                                || (enemy.x > this.screenWidth
                                + this.offsetXMax[(this.level - 1)]))
                            enemy.type = 0;
                    } else if (enemy.action == 14) {
                        enemy.ctr = 20;
                    }

                    if ((this.newCtr == 0)
                            && (this.freezeCtr == 0)
                            && (enemy.type < 20)
                            && (this.player.x + this.player.width > enemy.x
                            - enemy.sight)
                            && (this.player.x < enemy.x + enemy.width
                            + enemy.sight)
                            && (this.player.y + this.player.height > enemy.y
                            - enemy.sight)
                            && (this.player.y < enemy.y + enemy.height
                            + enemy.sight)) {
                        int randomAction = (this.rdm.nextInt() >>> 1) % 10;
                        if (randomAction < 1) {
                            enemy.action = 2;
                            enemy.ctr = 27;
                        } else if (randomAction < 8) {
                            if (enemy.level > this.player.level)
                                enemy.action = 3;
                            else {
                                enemy.action = 4;
                            }
                            enemy.ctr = 27;
                        } else {
                            if (enemy.level > this.player.level)
                                enemy.action = 4;
                            else {
                                enemy.action = 3;
                            }
                            enemy.ctr = 27;
                        }
                    } else if ((enemy.type < 20) && (this.freezeCtr == 0)) {
                        if (((enemy.x < -enemy.width) && (enemy.direction == 1))
                                || ((enemy.x > this.screenWidth) && (enemy.direction == 0))) {
                            enemy.action = 1;
                            enemy.ctr = 9;
                        }
                        if (enemy.direction == 0)
                            enemy.x += 2;
                        else if (enemy.direction == 1) {
                            enemy.x -= 2;
                        }
                        if (this.counter % 3 == 0) {
                            int r = (this.rdm.nextInt() >>> 1) % 3 - 1;
                            enemy.y += r;
                            if (enemy.y < 0) {
                                enemy.y = 0;
                            }
                            if (enemy.y > this.screenHeight - enemy.height) {
                                enemy.y = (this.screenHeight - enemy.height);
                            }
                        }
                    }
                }
                if ((this.deadCtr != 0)
                        || (enemy.type <= 0)
                        || ((enemy.type >= 27) && (enemy.type != 27) && (enemy.type != 9995))
                        || (this.player.x >= enemy.x + enemy.width)
                        || (this.player.x + this.player.width <= enemy.x)
                        || (this.player.y >= enemy.y + enemy.height)
                        || (this.player.y + this.player.height <= enemy.y))
                    continue;
                if ((this.player.level >= enemy.level)
                        && (this.gameOverCtr == 0)) {
                    killEnemy(enemy);
                    this.player.action = 13;
                    this.player.ctr = 6;
                    if (enemy.ext != G.OTHER) {
                        if (enemy.level == 0) {
                            this.smallEaten += 1;
                        }
                        if (enemy.level == 1) {
                            this.middleEaten += 1;
                        }
                        if (enemy.level == 2)
                            this.largeEaten += 1;
                    }
                } else if ((this.freezeCtr == 0) && (this.newCtr == 0)
                        && (this.gameOverCtr == 0)) {
                    if (enemy.type != 9995) {
                        playerDead();
                    } else {
                        this.player.action = 15;
                        this.player.ctr = 20;
                    }
                }
            }
    }

    private SP getFreeSprite() {
        //System.out.println("===========  getFreeSprite");
        for (int i = 0; i < this.sprite.length; i++) {
            if (this.sprite[i].type == 0) {
                SP sp = this.sprite[i];
                sp.x = 0;
                sp.y = 0;
                sp.ctr = 0;
                sp.width = 0;
                sp.height = 0;
                sp.direction = 0;
                sp.action = 0;
                sp.value = 0;
                sp.ext = 0;
                return sp;
            }
        }
        return null;
    }

    private void initPlayer() {
        System.out.println("****************GAME Source initPlayer() 001.....");
        System.out.println("****************GAME Source initPlayer() 002.....");
        this.player = getFreeSprite();
        if (this.player != null) {
            this.player.type = 1;
            this.player.level = 2;
            this.player.width = 37;
            this.player.height = 28;
            this.player.x = (this.screenWidth / 2 - this.player.width / 2);
            this.player.y = (this.screenHeight / 2 - this.player.height / 2);
            this.player.direction = 0;
            this.player.action = 0;
        }
    }

    private void addEnemy() {
        //System.out.println("****************GAME Source addEnemy().....");

        for (int i = 0; i < this.smallCount[(this.level - 1)]; i++) {
            initEnemy(this.small[(this.level - 1)], 0);
        }
        for (int i = 0; i < this.middleCount[(this.level - 1)]; i++) {
            initEnemy(this.middle[(this.level - 1)], 1);
        }
        for (int i = 0; i < this.largeCount[(this.level - 1)]; i++) {
            initEnemy(this.large[(this.level - 1)], 2);
        }
        initConch();

    }

    private void initEnemy(int type, int level) {
        //System.out.println("===========  initEnemy");
        SP enemy = getFreeSprite();
        if (enemy != null) {
            enemy.type = type;
            enemy.level = level;
            if (level == 0) {
                enemy.width = 12;
                enemy.height = 10;
            } else if (level == 1) {
                enemy.width = 17;
                enemy.height = 14;
            } else if (level == 2) {
                enemy.width = 24;
                enemy.height = 20;
            }
            int rx = (this.rdm.nextInt() >>> 1) % 2;
            int r = (this.rdm.nextInt() >>> 1)
                    % (this.offsetXMax[(this.level - 1)] + enemy.width);
            if (rx == 0) {
                r = -r;
            }
            enemy.x = (-enemy.width + (this.screenWidth + enemy.width) * rx + r);
            enemy.y = ((this.rdm.nextInt() >>> 1)
                    % (this.screenHeight - enemy.height - 0) + 0);
            enemy.direction = rx;
            enemy.action = 0;
            enemy.sight = (32 + level * 4);
        }
    }

    private void initConch() {
        //System.out.println("===========  initConch");
        SP conch = getFreeSprite();
        if (conch != null) {
            conch.type = 9998;
            conch.level = 3;
            conch.width = 23;
            conch.height = 23;
            conch.x = (this.screenWidth / 2 - conch.width / 2);
            conch.y = (this.screenHeight - conch.height);
            conch.action = 6;
            conch.ctr = 50;
        }
    }

    private void initShark() {
        //System.out.println("===========  initShark");
        SP shark = getFreeSprite();
        if (shark != null) {
            shark.type = 27;
            shark.level = 3;
            shark.width = 90;
            shark.height = 32;
            int r = (this.rdm.nextInt() >>> 1) % 2;
            shark.x = (-shark.width + this.offsetXMin[(this.level - 1)] + (this.screenWidth
                    + shark.width - this.offsetXMin[(this.level - 1)] + this.offsetXMax[(this.level - 1)])
                    * r);
            shark.direction = r;
            shark.y = ((this.rdm.nextInt() >>> 1)
                    % (this.screenHeight + this.offsetYMax[(this.level - 1)] - shark.height) - this.offsetYMax[(this.level - 1)]);
            shark.action = 12;
        }
    }

    private void initShuimu() {

        //System.out.println("===========  initShuimu");
        SP shuimu = getFreeSprite();
        if (shuimu != null) {
            shuimu.type = 9995;
            shuimu.level = 3;
            shuimu.width = 28;
            shuimu.height = 35;
            shuimu.x = ((this.rdm.nextInt() >>> 1)
                    % (this.screenWidth - this.offsetXMin[(this.level - 1)]
                    + this.offsetXMax[(this.level - 1)] - shuimu.width) + this.offsetXMin[(this.level - 1)]);
            shuimu.y = (this.screenHeight + this.offsetY);
            shuimu.action = 14;
            shuimu.ctr = 14;
        }
    }

    private void initPrecious(SP conch) {
        //System.out.println("===========  initPrecious");
        SP precious = getFreeSprite();
        if (precious != null) {
            int r = (this.rdm.nextInt() >>> 1) % G.MAX_CONCH_RDM;
            if (r < G.NULL_CONCH_RDM)
                precious.type = 0;
            else if (r >= G.BLACK_CONCH_RDM)
                precious.type = 21;
            else {
                precious.type = 20;
            }
            precious.level = 0;
            precious.width = 8;
            precious.height = 9;
            conch.x += 7;
            conch.y += 7;
            precious.action = 9;
            precious.ctr = 0;
            precious.ext = G.OTHER;
        }
    }

    private void initBubble() {
        //System.out.println("===========  initBubble");
        SP bubble = getFreeSprite();
        if (bubble != null) {
            int s = (this.rdm.nextInt() >>> 1) % 2;
            bubble.type = (9996 + s);
            bubble.level = 9999;
            bubble.width = 30;
            bubble.height = 30;
            bubble.x = ((this.rdm.nextInt() >>> 1)
                    % (this.screenWidth - this.offsetXMin[(this.level - 1)]
                    + this.offsetXMax[(this.level - 1)] - bubble.width) + this.offsetXMin[(this.level - 1)]);
            bubble.y = (this.screenHeight + this.offsetY);
            bubble.action = 10;
            bubble.value = randomBubble();
        }
    }

    private void initBubblePrecious() {
        //System.out.println("===========  initBubblePrecious");
        SP bubblePrecious = getFreeSprite();
        if (bubblePrecious != null) {
            int r = (this.rdm.nextInt() >>> 1) % 100;
            if (this.level == 1)
                bubblePrecious.type = 22;
            else if (this.level == 2) {
                if (r < 10)
                    bubblePrecious.type = 23;
                else
                    bubblePrecious.type = 22;
            } else if ((this.level == 3) || (this.level == 4)) {
                if (r < 10)
                    bubblePrecious.type = 23;
                else if (r < 12)
                    bubblePrecious.type = 24;
                else
                    bubblePrecious.type = 22;
            } else if (this.level == 5) {
                if (r < 10)
                    bubblePrecious.type = 23;
                else if (r < 12)
                    bubblePrecious.type = 24;
                else if (r < 20)
                    bubblePrecious.type = 25;
                else {
                    bubblePrecious.type = 22;
                }
            } else if (r < 10)
                bubblePrecious.type = 23;
            else if (r < 12)
                bubblePrecious.type = 24;
            else if (r < 20)
                bubblePrecious.type = 25;
            else if (r < 35)
                bubblePrecious.type = 26;
            else {
                bubblePrecious.type = 22;
            }

            bubblePrecious.ext = G.OTHER;
            bubblePrecious.level = 0;
            bubblePrecious.width = 15;
            bubblePrecious.height = 15;
            bubblePrecious.x = ((this.rdm.nextInt() >>> 1)
                    % (this.screenWidth - this.offsetXMin[(this.level - 1)]
                    + this.offsetXMax[(this.level - 1)] - bubblePrecious.width) + this.offsetXMin[(this.level - 1)]);
            bubblePrecious.y = (this.screenHeight + this.offsetY);
            bubblePrecious.action = 11;
        }
    }

    private void initScore(int value, int x, int y) {
        //System.out.println("===========  initScore");
        if (this.doubleCtr > 0) {
            this.score += value * this.hit;
            this.collected += value * this.hit;
        } else {
            this.score += value * this.hit / 2;
            this.collected += value * this.hit / 2;
        }
        if ((this.collected > this.goal[(this.level - 1)] / 3)
                && (this.player.level == 0)) {
            this.yummyCtr = 50;
            this.player.level = 2;
            this.player.width = 37;
            this.player.height = 28;
        }
        if ((this.collected > this.goal[(this.level - 1)] * 2 / 3)
                && (this.player.level == 1)) {
            this.yummyCtr = 50;
            this.player.level = 2;
            this.player.width = 37;
            this.player.height = 28;
        }
        if (this.collected >= this.goal[(this.level - 1)]) {
            gameState = 2;
            this.levelCompleteCtr = 10;
        }

        if (this.collected < this.goal[(this.level - 1)] * 0.166D)
            G.AD_LEVEL = 0;
        else if (this.collected < this.goal[(this.level - 1)] * 0.332D)
            G.AD_LEVEL = 1;
        else if (this.collected < this.goal[(this.level - 1)] * 0.498D)
            G.AD_LEVEL = 2;
        else if (this.collected < this.goal[(this.level - 1)] * 0.664D)
            G.AD_LEVEL = 3;
        else if (this.collected < this.goal[(this.level - 1)] * 0.83D)
            G.AD_LEVEL = 4;
        else {
            G.AD_LEVEL = 5;
        }

        SP score = getFreeSprite();
        if (score != null) {
            score.type = 9999;
            score.level = 9999;
            score.ctr = 15;
            score.action = 5;
            score.x = x;
            score.y = y;
            if (this.doubleCtr > 0)
                score.value = (value * this.hit);
            else
                score.value = (value * this.hit / 2);
        }
    }

    private void initLevel(int level) {
        this.collected = 0;
        this.smallEaten = 0;
        this.middleEaten = 0;
        this.largeEaten = 0;
        G.AD_LEVEL = 0;
        G.AD_RATE = 0.0F;
        for (int i = 0; i < this.sprite.length; i++) {
            this.sprite[i].type = 0;
        }
        initPlayer();
        this.newCtr = 50;
        addEnemy();
    }

    private void killEnemy(SP enemy) {
        if (enemy.type < 20) {
            initEnemy(enemy.type, enemy.level);
            initScore((enemy.level + 1) * 20, enemy.x, enemy.y);
        } else if (enemy.type < 22) {
            if (enemy.type == 20) {
                int r = (this.rdm.nextInt() >>> 1) % 3 + 1;
                initScore(G.WHITE_CONSH_SCORE * r, enemy.x, enemy.y);
            } else if (enemy.type == 21) {
                initScore(G.WHITE_CONSH_SCORE, enemy.x, enemy.y);
            } else if (this.player.level < 2) {
                this.player.level += 1;
                if (this.player.level == 1)
                    this.collected = (this.goal[(this.level - 1)] / 3);
                else
                    this.collected = (this.goal[(this.level - 1)] * 2 / 3);
            } else {
                this.collected = this.goal[(this.level - 1)];
                gameState = 2;
                this.levelCompleteCtr = 10;
            }
        } else if (enemy.type == 22) {
            initScore(60, enemy.x, enemy.y);
        } else if (enemy.type == 23) {
            this.doubleCtr = 100;
        } else if (enemy.type == 24) {
            this.rest += 1;
        } else if (enemy.type == 25) {
            this.speedCtr = 100;
        } else if (enemy.type == 26) {
            this.freezeCtr = 100;
        }
        enemy.type = 0;
    }

    private void playerDead() {
        if (this.rest == 0) {
            this.gameOverCtr = 100;
        } else {
            this.rest -= 1;
            int bl = this.player.level;
            this.player.type = 0;
            initPlayer();
            this.deadCtr = 50;
            this.newCtr = 100;
            this.player.level = bl;
            if (this.player.level == 0)
                this.collected = 0;
            else if (this.player.level == 1)
                this.collected = (this.goal[(this.level - 1)] / 3);
            else if (this.player.level == 2)
                this.collected = (this.goal[(this.level - 1)] * 2 / 3);
        }
    }

    private void randomShark() {
        int r = (this.rdm.nextInt() >>> 1) % 150;
        if (r == 0) {
            this.sharkCtr = 250;
            initShark();
        }
    }

    private void randomShuimu() {
        int r = (this.rdm.nextInt() >>> 1) % 100;
        if (r == 0)
            initShuimu();
    }

    public void hideNotify() {
        pauseFlag = true;
    }

    public void showNotify() {
        this.keyBack = false;
        this.keyUp = false;
        this.keyDown = false;
        this.keyLeft = false;
        this.keyRight = false;
        this.keyFire = false;
        this.deadCtr = 0;
        this.gameOverCtr = 0;
    }

    private void drawString(Graphics g, String str, int x, int y, int anchor) {
        g.setColor(0, 0, 0);
        Font boldFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        g.setFont(boldFont);
        g.drawString(str, x, y, anchor);
    }
}