package cn.candy.hook;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import cn.candy.relic.aoman;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 加载遗物 用于在游戏中注入你修改的内容
 *
 * @author : Administrator
 * @date : 2020-08-05 17:01
 **/
@SpireInitializer
@SuppressWarnings("unused")
public class LoadMySpireMod implements PostDungeonInitializeSubscriber, EditRelicsSubscriber, EditStringsSubscriber {
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
        tryGetRelic(new aoman());
        logger.info(">>>初始化完成<<<");
    }
    
    /**
     * 在游戏模组中加入新遗物
     */
    @Override
    public void receiveEditRelics() {
        logger.info(">>>尝试在游戏中加载自定义遗物属性开始<<<");
        logger.info(">>>尝试在游戏中加载【{}】遗物数据<<<", aoman.ID);
        BaseMod.addRelic(new aoman(), RelicType.SHARED);
        logger.info(">>>尝试在游戏中加载自定义遗物属性完毕<<<");
    }
    
    /**
     * 在游戏中加载本mod的json文件
     */
    @Override
    public void receiveEditStrings() {
        receiveJson("遗物", "MyNewCustomRelicList.json", RelicStrings.class);
    }
    
    /**
     * 加载json文件
     *
     * @param typeInfo     遗物描述，用于日志输出
     * @param jsonFileName 文件名，连带json后缀 如： "MyNewCustomCardList.json"
     * @param className    接收该描述文件的类
     */
    private void receiveJson(String typeInfo, String jsonFileName, Class<?> className) {
        logger.info(">>>准备加载[{}]的描述json文件<<<", typeInfo);
        String relicStrings = Gdx.files.internal("localization/" + jsonFileName).readString("UTF-8");
        BaseMod.loadCustomStrings(className, relicStrings);
        logger.info(">>>加载[{}]的json文件结束<<<", typeInfo);
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
}
