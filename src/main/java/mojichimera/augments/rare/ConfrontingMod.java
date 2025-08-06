package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

import java.util.Iterator;

public class ConfrontingMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ConfrontingMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 1.5F;
    private static final int PERCENTAGE = 50;
    private boolean modMagic;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;
    }

    @Override
    public boolean atBattleStartPreDraw(AbstractCard card) {
        boolean activated = shouldActivate();
        if (activated) {
            if (card.baseDamage > 0) {
                card.baseDamage = (int) (card.baseDamage * MULTIPLIER);
                card.damage = card.baseDamage;
                card.isDamageModified = true;
            }
            if (card.baseBlock > 0) {
                card.baseBlock = (int) (card.baseBlock * MULTIPLIER);
                card.block = card.baseBlock;
                card.isBlockModified = true;
            }
            if (this.modMagic && card.baseMagicNumber > 0) {
                card.baseMagicNumber = (int) (card.baseMagicNumber * MULTIPLIER);
                card.magicNumber = card.baseMagicNumber;
                card.isMagicNumberModified = true;
            }
        }
        return activated;
    }

    @Override
    public void onCreatedMidCombat(AbstractCard card) {
        atBattleStartPreDraw(card);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.reachesVariable(card, 2)
                && AugmentHelper.isNormal(card);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PERCENTAGE));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ConfrontingMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    private boolean shouldActivate() {
        if (!MojiHelper.isInCombat()) {
            return false;
        }
        boolean activated = AbstractDungeon.getCurrRoom().eliteTrigger;
        Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var3.hasNext()) {
            AbstractMonster m = (AbstractMonster)var3.next();
            if (m.type == AbstractMonster.EnemyType.BOSS) {
                activated = true;
                break;
            }
        }

        return activated;
    }
}
