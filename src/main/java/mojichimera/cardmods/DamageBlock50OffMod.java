package mojichimera.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.mojichimera;

public class DamageBlock50OffMod extends AbstractCardModifier {
    public static final String ID = mojichimera.makeID(DamageBlock50OffMod.class.getSimpleName());
    private static final float MULTIPLIER = 0.5F;

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * MULTIPLIER;
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * MULTIPLIER;
        return block;
    }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new DamageBlock50OffMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
