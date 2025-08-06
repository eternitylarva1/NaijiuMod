package mojichimera.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mojichimera.mojichimera;

public class BiasedPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = mojichimera.makeID(BiasedPower.class.getSimpleName());

    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

//    private static final PowerStrings TEXT = new PowerStrings();
    private final String basePowerName;
    private final String basePowerID;
    private int turn;
    private static int idCounter = 0;

//    static {
//        if (Settings.language.toString().toLowerCase().equals("zhs")) {
//            TEXT.NAME = "偏差";
//            TEXT.DESCRIPTIONS = new String[] {"在你的回合开始时，失去 #b", " 点 #y", " 。持续 #b", " 回合。", " 回合。"};
//        } else {
//            TEXT.NAME = "Biased ";
//            TEXT.DESCRIPTIONS = new String[] {"At the start of each turn, lose #b", " #y", ". Lasts #b", " turn.", " turns."};
//        }
//    }

    public BiasedPower(AbstractCreature owner, AbstractPower powerToLose, int amount, int turn) {
        this.name = TEXT.NAME + powerToLose.name;
        idCounter++;
        this.ID = POWER_ID + idCounter;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
//        this.region128 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath("BiasedPower128.png")), 0, 0, 128, 128);
//        this.region48 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath("BiasedPower48.png")), 0, 0, 48, 48);
        this.loadRegion("bias");
        this.basePowerName = powerToLose.name;
        this.basePowerID = powerToLose.ID;
        this.turn = turn;
        this.isTurnBased = true;
        updateDescription();
    }

    public void updateDescription() {
        this.description = String.format("%s%d%s%s%s%d%s",
                TEXT.DESCRIPTIONS[0],
                this.amount,
                TEXT.DESCRIPTIONS[1],
                this.basePowerName,
                TEXT.DESCRIPTIONS[2],
                this.turn,
                (this.turn == 1 ? TEXT.DESCRIPTIONS[3] : TEXT.DESCRIPTIONS[4]));
    }

    @Override
    public void atStartOfTurn() {
        flash();
        AbstractPower powerToLose = null;
        for (AbstractPower power : this.owner.powers) {
            if (power.ID.equals(this.basePowerID)) {
                powerToLose = power;
                break;
            }
        }
        if (powerToLose != null) {
            Class<? extends AbstractPower> powerClass = powerToLose.getClass();
            try {
                AbstractPower newPower = powerClass.getConstructor(AbstractCreature.class, int.class).newInstance(this.owner, -this.amount);
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, newPower, -this.amount));
                this.turn--;
                updateDescription();
                if (this.turn <= 0) {
                    this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                }
                if (powerToLose.amount - this.amount == 0) {
                    this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.basePowerID));
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
        }
    }

    public AbstractPower makeCopy() {
        return new BiasedPower(this.owner, this.owner.getPower(this.basePowerID), this.amount, this.turn);
    }
}

