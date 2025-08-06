package mojichimera.powers;

import Naijiumod.utils.FormatHelper;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mojichimera.mojichimera;

public class EveryTurnStartPlayPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = mojichimera.makeID(EveryTurnStartPlayPower.class.getSimpleName());

    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

//    private static final PowerStrings TEXT = new PowerStrings();
    private final AbstractCard card;

//    static {
//        if (Settings.language.toString().toLowerCase().equals("zhs")) {
//            TEXT.NAME = "免费";
//            TEXT.DESCRIPTIONS = new String[] {"在你的回合开始时，打出 #b%1$s 张 %2$s。"};
//        } else {
//            TEXT.NAME = "Free ";
//            TEXT.DESCRIPTIONS = new String[] {"At the start of your turn, play #b%1$s copies of %2$s."};
//        }
//    }

    public EveryTurnStartPlayPower(AbstractCreature owner, AbstractCard card, int amount) {
        this.name = TEXT.NAME + card.name;
        this.ID = POWER_ID + card.uuid;
        this.owner = owner;
        this.amount = amount;
        this.card = card.makeSameInstanceOf();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.loadRegion("establishment");
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(TEXT.DESCRIPTIONS[0], this.amount, FormatHelper.prefixWords(this.card.name, "#y"));

        if (this.description.contains("copyreplace")) {
            this.description = this.description.replace("copyreplace", (amount == 1) ? "copy" : "copies");
        }
    }

    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            this.addToBot(new AbstractGameAction() {
                public void update() {
                    for(int i = 0; i < EveryTurnStartPlayPower.this.amount; ++i) {
                        AbstractCard tmp = EveryTurnStartPlayPower.this.card.makeSameInstanceOf();
                        AbstractDungeon.player.limbo.addToBottom(tmp);
                        tmp.current_x = EveryTurnStartPlayPower.this.card.current_x;
                        tmp.current_y = EveryTurnStartPlayPower.this.card.current_y;
                        tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                        tmp.target_y = (float)Settings.HEIGHT / 2.0F;
                        tmp.purgeOnUse = true;
                        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, true, EveryTurnStartPlayPower.this.card.energyOnUse, true, true), true);
                    }

                    this.isDone = true;
                }
            });
        }

    }

    public AbstractPower makeCopy() {
        return new EveryTurnStartPlayPower(this.owner, this.card, this.amount);
    }
}
