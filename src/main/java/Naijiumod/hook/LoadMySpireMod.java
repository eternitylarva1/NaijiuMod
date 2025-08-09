package Naijiumod.hook;



import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.cardModifier.DynvarCarrier;
import Naijiumod.cardModifier.ItemMod;
import Naijiumod.helpers.DynamicDynamicVariableManager;
import Naijiumod.screens.ChooseModifierScreen;
import Naijiumod.screens.MyScreen;
import Naijiumod.ui.FumoEffect;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import static chimeracardsplus.ChimeraCardsPlus.*;
import static com.megacrit.cardcrawl.core.Settings.language;
import static mojichimera.mojichimera.getModID;
import static mojichimera.mojichimera.loadLocalizationIfAvailable;
/**
 * 加载遗物 用于在游戏中注入你修改的内容
 *
 * @author : Administrator
 * @date : 2020-08-05 17:01
 **/
@SpireInitializer
@SuppressWarnings("unused")
public class LoadMySpireMod implements EditKeywordsSubscriber,OnStartBattleSubscriber,PostInitializeSubscriber,EditCardsSubscriber,PostDungeonUpdateSubscriber,StartActSubscriber,PostDungeonInitializeSubscriber, EditRelicsSubscriber, EditStringsSubscriber {
    /**
     * 日志对象 用来输出日志 指定本类 LoadMyEasyMod 以确认日志的输出对象
     */
    private static final Logger logger = LogManager.getLogger(LoadMySpireMod.class);
    
    public static void initialize() {
        new LoadMySpireMod();
    }
    
    public LoadMySpireMod() {
        BaseMod.subscribe(this);
    }
    
    /**
     * 初始化时添加本遗物
     */
    @Override
    public void receivePostDungeonInitialize() {
        logger.info(">>>初始化开始<<<");
        //给人物添加遗物

        logger.info(">>>初始化完成<<<");
    }
    public static String ModID="Naijiu";

    /*     */   public static void applyWeightedCardMod(AbstractCard c, AbstractAugment.AugmentRarity rarity, int index) {
        /* 833 */     ArrayList<AbstractAugment> validMods = new ArrayList<>();
        /* 834 */     switch (rarity) {
            /*     */       case COMMON:
                /* 836 */         validMods.addAll((Collection<? extends AbstractAugment>)commonMods.stream().filter(m -> (m.canApplyTo(c) )).collect(Collectors.toCollection(ArrayList::new)));
                /*     */         break;
            /*     */       case UNCOMMON:
                /* 839 */         validMods.addAll((Collection<? extends AbstractAugment>)uncommonMods.stream().filter(m -> (m.canApplyTo(c))).collect(Collectors.toCollection(ArrayList::new)));
                /*     */         break;
            /*     */       case RARE:
                /* 842 */         validMods.addAll((Collection<? extends AbstractAugment>)rareMods.stream().filter(m -> (m.canApplyTo(c) )).collect(Collectors.toCollection(ArrayList::new)));
                /*     */         break;
            /*     */     }
        /* 845 */     if (!validMods.isEmpty()) {
            /* 846 */       AbstractCardModifier m = ((AbstractAugment)validMods.get(AbstractDungeon.miscRng.random(validMods.size() - 1))).makeCopy();
            /* 847 */       CardModifierManager.addModifier(c, m);
            /* 848 */
            /*     */     }
        /*     */   }

    /**
     * 在游戏模组中加入新遗物
     */
    @Override
    public void receiveEditRelics() {
        logger.info(">>>尝试在游戏中加载自定义遗物属性开始<<<");
   /*
        new AutoAdd(ModID)
                .packageFilter(aoman.class)
                .any(CustomRelic.class, (info, relic) -> {
                    BaseMod.addRelic(relic,RelicType.SHARED);

                    UnlockTracker.markRelicAsSeen(relic.relicId);

                });
*/


        logger.info(">>>尝试在游戏中加载自定义遗物属性完毕<<<");
    }
    
    /**
     * 在游戏中加载本mod的json文件
     */
    @Override
    public void receiveEditStrings() {

        receiveJson("UI", "uistrings.json", UIStrings.class);
        receiveJson("Power", "powerstrings.json", PowerStrings.class);
        logger.info("Editing strings");

        // UI Strings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/"+loadLocalizationIfAvailable("mojichimera-UI-Strings.json"));

        // Augment Strings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/"+loadLocalizationIfAvailable("mojichimera-Augment-Strings.json"));

        // Power Strings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/"+loadLocalizationIfAvailable("mojichimera-Power-Strings.json"));

        if (!DEFAULT_LANGUAGE.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }
    public static final ArrayList<AbstractAugment> commonMods = new ArrayList<>();

    public static final ArrayList<AbstractAugment> uncommonMods = new ArrayList<>();

    public static final ArrayList<AbstractAugment> rareMods = new ArrayList<>();

    public static final ArrayList<AbstractAugment> specialMods = new ArrayList<>();

    public static final HashMap<String, AbstractAugment> modMap = new HashMap<>();
    public static MyScreen gridCardSelectScreen1 ;
    public static ChooseModifierScreen gridCardSelectScreen2 ;
    /**
     * 加载json文件
     *
     * @param typeInfo     遗物描述，用于日志输出
     * @param jsonFileName 文件名，连带json后缀 如： "MyNewCustomCardList.json"
     * @param className    接收该描述文件的类
     */
    private void receiveJson(String typeInfo, String jsonFileName, Class<?> className) {
        logger.info(">>>准备加载[{}]的描述json文件<<<", typeInfo);
        String lang;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "ZHS/"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
        } else {
            lang = "ENG/"; // 如果没有相应语言的版本，默认加载英语
        }
        String relicStrings = Gdx.files.internal("NaijiuModResources/localization/" +lang+ jsonFileName).readString("UTF-8");
        BaseMod.loadCustomStrings(className, relicStrings);
        logger.info(">>>加载[{}]的json文件结束<<<", typeInfo);
        loadLocalization(DEFAULT_LANGUAGE);

    }

    
    /**
     * 根据遗物id添加给当前人物遗物 如果没有则添加，已有会忽略
     *
     * @param customRelic 遗物
     */
    private void tryGetRelic(CustomRelic customRelic) {
        if (!AbstractDungeon.player.hasRelic(customRelic.relicId)) {
            logger.info(">>>人物没有遗物【{}】,尝试给人物添加遗物【{}】<<<", customRelic.relicId, customRelic.relicId);
            int slot = AbstractDungeon.player.getRelicNames().size();
           // customRelic.instantObtain(AbstractDungeon.player, slot, false);
            logger.info(">>>尝试给人物添加遗物【{}】成功<<<", customRelic.relicId);
        }
    }



    public void receivePreDungeonUpdate() {


    }

    @Override
    public void receiveStartAct() {

    }

    @Override
    public void receivePostDungeonUpdate() {

    }

    @Override
    public void receiveEditCards() {


    }
    /*     */   @SpirePatch2(clz = CardCrawlGame.class, method = "create")
    /*     */   public static class PostLoadFontsPatch
            /*     */   {
        /*     */     @SpireInsertPatch(locator = Locator.class)
        /*     */     public static void load() {
            /* 924 */       ;
            /*     */     }
        /*     */
        /*     */     public static class Locator
                /*     */       extends SpireInsertLocator {
            /*     */       public int[] Locate(CtBehavior ctBehavior) throws Exception {
                /* 930 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "initializeDynamicFrameWidths");
                /* 931 */         return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
                /*     */       }
            /*     */     }
        /*     */   }
    @Override
    public void receivePostInitialize() {
        BaseMod.registerModBadge(ImageMaster.loadImage("NaijiuModResources/localization/relics/bell.png"),ModID,"Dieyou", "耐久决定方式:稀有度基数*卡牌类型基数。下方可以调整对应的基数", new MyModConfig());
        (new AutoAdd(ModID))
                .packageFilter("Naijiumod.cardModifier")
                .any(AbstractAugment.class, (info, abstractAugment) -> registerAugment(abstractAugment, ModID));
        (new AutoAdd(ModID))
                .packageFilter("mojichimera.augments")
                .any(AbstractAugment.class, (info, abstractAugment) -> registerAugment(abstractAugment, ModID));
    new AutoAdd(ModID)
                .packageFilter("chimeracardsplus.cardmods")
                .any(AbstractAugment.class, (info, abstractAugment) ->  registerAugment(abstractAugment, modID));

        logger.info("Done loading card mods");
        gridCardSelectScreen1 = new MyScreen();
        gridCardSelectScreen2 = new ChooseModifierScreen();
      BaseMod.addCustomScreen(gridCardSelectScreen1);
      BaseMod.addCustomScreen(gridCardSelectScreen2);
        BaseMod.addDynamicVariable((DynamicVariable) DynamicDynamicVariableManager.instance);
        /*     */
    }

    public static void registerAugment(AbstractAugment a, String modID) {
        if (!a.identifier(null).isEmpty()) {
            modMap.put(a.identifier(null), a);
        } else {
            logger.warn("Augment " + a + " does not set an identifier, Chimera Cards can not add this mod via console commands!");
        }
        if (a instanceof DynvarCarrier) {
            /* 255 */       DynamicDynamicVariableManager.registerDynvarCarrier((DynvarCarrier)a);
            /*     */     }
        switch (a.getModRarity()) {
            case COMMON:
                commonMods.add(a);
                break;
            case UNCOMMON:
                commonMods.add(a);
                break;
            case RARE:
                uncommonMods.add(a);
                break;
            case SPECIAL:
                specialMods.add(a);
                break;
        }
    }
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

          for (AbstractCard card:AbstractDungeon.player.drawPile.group)
          {
              if(StSLib.getMasterDeckEquivalent(card)!=null){
                  if(CardModifierManager.hasModifier(card,ItemMod.ID)){
                       ItemMod itemMod = (ItemMod)CardModifierManager.getModifiers(card, ItemMod.ID).get(0);
                       if(StSLib.getMasterDeckEquivalent(card)==null){
                           break;
                       }
                       if(CardModifierManager.getModifiers(StSLib.getMasterDeckEquivalent(card), ItemMod.ID).isEmpty()){
                           break;
                       }
                       ItemMod itemMod1 = (ItemMod)CardModifierManager.getModifiers(StSLib.getMasterDeckEquivalent(card), ItemMod.ID).get(0);
                       itemMod.uses=itemMod1.uses;
                       card.initializeDescription();
                       itemMod.damo  = itemMod1.damo;
                       card.initializeDescription();

                  }
              }
          }
        }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "ENG";
        if (language == Settings.GameLanguage.ZHS) {
            lang = "ZHS";
        }

        String json = Gdx.files.internal("NaijiuModResources/localization/"+lang+"/keywords.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                // 这个id要全小写
                BaseMod.addKeyword("naijiu", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
}
