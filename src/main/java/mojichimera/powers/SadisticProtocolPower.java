package mojichimera.powers;

import Naijiumod.utils.FormatHelper;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class SadisticProtocolPower extends AbstractPower implements NonStackablePower {
    public static final String POWER_ID = mojichimera.makeID(SadisticProtocolPower.class.getSimpleName());

    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private final AbstractCard card;
    public static final SpireField<Boolean> isSadisticProtocol = new SpireField<>(() -> false);

    public SadisticProtocolPower(AbstractCreature owner, AbstractCard card, int amount) {
        this.name = TEXT.NAME;
        this.ID = POWER_ID;
        this.amount = amount;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.card = card;
        this.isTurnBased = false;
        this.loadRegion("sadistic");
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(TEXT.DESCRIPTIONS[0], this.amount, FormatHelper.prefixWords(this.card.name, "#y"));

        if (this.description.contains("copyreplace")) {
            this.description = this.description.replace("copyreplace", (amount == 1) ? "copy" : "copies");
        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        AbstractCard card = MojiHelper.getLastCardPlayedThisTurn();
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()
                && (power.type == PowerType.DEBUFF && !power.ID.equals("Shackled") && source == this.owner && target != this.owner && !target.hasPower("Artifact"))
                && (card != null && !isSadisticProtocol.get(card))) {
            AbstractMonster m = (target instanceof AbstractMonster) ? (AbstractMonster) target : null;
            this.flash();
            this.addToBot(new AbstractGameAction() {
                public void update() {
                    for (int i = 0; i < SadisticProtocolPower.this.amount; ++i) {
                        AbstractCard tmp = SadisticProtocolPower.this.card.makeSameInstanceOf();
                        AbstractDungeon.player.limbo.addToBottom(tmp);
                        tmp.current_x = SadisticProtocolPower.this.card.current_x;
                        tmp.current_y = SadisticProtocolPower.this.card.current_y;
                        tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                        tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                        tmp.purgeOnUse = true;
                        isSadisticProtocol.set(tmp, true);
                        if (tmp.target == AbstractCard.CardTarget.ENEMY && m != null && !m.isDeadOrEscaped())
                            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, SadisticProtocolPower.this.card.energyOnUse, true, true), true);
                        else
                            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, true, SadisticProtocolPower.this.card.energyOnUse, true, true), true);
                    }

                    this.isDone = true;
                }
            });
        }
    }
}
