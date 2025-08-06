package mojichimera.damagemods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WallopIntentDamage extends AbstractDamageModifier {
    public WallopIntentDamage() {
    }

    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (lastDamageTaken > 0 && target instanceof AbstractMonster && ((AbstractMonster)target).getIntentBaseDmg() >= 0) {
            addToTop(new GainBlockAction(AbstractDungeon.player, lastDamageTaken));
        }
    }

    public boolean isInherent() {
        return true;
    }

    public AbstractDamageModifier makeCopy() {
        return new WallopIntentDamage();
    }
}
