package mojichimera.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mojichimera.mojichimera;

public class LosePowerPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = mojichimera.makeID(LosePowerPower.class.getSimpleName());

    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

//    private static final PowerStrings TEXT = new PowerStrings();
    private final String basePowerName;
    private final String basePowerID;

//    static {
//        if (Settings.language.toString().toLowerCase().equals("zhs")) {
//            TEXT.NAME = "下降";
//            TEXT.DESCRIPTIONS = new String[] {"在你的回合结束时，失去 #b", " 点 #y", " 。"};
//        } else {
//            TEXT.NAME = " Down";
//            TEXT.DESCRIPTIONS = new String[] {"At the end of this turn, lose #b", " #y", "."};
//        }
//    }

    public LosePowerPower(AbstractCreature owner, AbstractPower powerToLose, int amount) {
        this.name = powerToLose.name + TEXT.NAME;
        this.ID = POWER_ID + powerToLose.ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
//        this.region128 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath("LosePowerPower84.png")), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath("LosePowerPower32.png")), 0, 0, 32, 32);
        this.loadRegion("flex");
        this.basePowerName = powerToLose.name;
        this.basePowerID = powerToLose.ID;
        this.isTurnBased = false;
        updateDescription();
    }

    public void updateDescription() {
        this.description = String.format("%s%d%s%s%s",
                TEXT.DESCRIPTIONS[0],
                this.amount,
                TEXT.DESCRIPTIONS[1],
                this.basePowerName,
                TEXT.DESCRIPTIONS[2]);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
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
                if (powerToLose.amount - this.amount == 0)
                    this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.basePowerID));
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            } catch (Exception e) {
                e.printStackTrace();
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
        }
    }

    public AbstractPower makeCopy() {
        return new LosePowerPower(this.owner, this.owner.getPower(this.basePowerID), this.amount);
    }
}

