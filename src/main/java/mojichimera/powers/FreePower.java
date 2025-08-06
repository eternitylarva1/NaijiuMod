package mojichimera.powers;


import Naijiumod.power.BombPower;
import Naijiumod.utils.FormatHelper;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mojichimera.mojichimera;

public class FreePower extends AbstractPower implements NonStackablePower {
    public static final String POWER_ID = mojichimera.makeID(NextTurnStartPlayPower.class.getSimpleName());

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

    public FreePower(AbstractCreature owner, AbstractCard card, int amount) {
        this.name = TEXT.NAME + card.name;
        this.ID = POWER_ID;
        this.amount = amount;
        this.owner = owner;
        this.card = card.makeStatEquivalentCopy();
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

    public void atEndOfTurn(boolean isPlayer) {
        addToTop(new ApplyPowerAction(owner, owner, new BombPower(owner, 1, amount, card), 1));
    }
}
