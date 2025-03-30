package FanzhuanMod.cards;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import FanzhuanMod.hook.MyModConfig;
import com.megacrit.cardcrawl.actions.defect.NewThunderStrikeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import java.util.Iterator;

public class ThunderStrike extends AbstractCard {
    public static final String ID = "Thunder Strike";
    private static final CardStrings cardStrings;

    public ThunderStrike() {
        super("Thunder Strike", cardStrings.NAME, "blue/attack/thunder_strike", 3, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 0;
        this.magicNumber = 0;
        this.baseDamage = 7;
        this.tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseMagicNumber = 0;
        Iterator var3 = AbstractDungeon.actionManager.orbsChanneledThisCombat.iterator();

        while(var3.hasNext()) {
            AbstractOrb o = (AbstractOrb)var3.next();
            if(MyModConfig.EnableLighting) {
            if (o instanceof Frost) {
                ++this.baseMagicNumber;
            }
             }else {
                if (o instanceof Lightning) {
                    ++this.baseMagicNumber;
                }
            }
        }

        this.magicNumber = this.baseMagicNumber;

        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new NewThunderStrikeAction(this));
        }

    }

    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 0;
        this.magicNumber = 0;
        Iterator var1 = AbstractDungeon.actionManager.orbsChanneledThisCombat.iterator();

        while(var1.hasNext()) {
            AbstractOrb o = (AbstractOrb)var1.next();
            if(MyModConfig.EnableLighting) {
                if (o instanceof Frost) {
                    ++this.baseMagicNumber;
                }
            }else {
                if (o instanceof Lightning) {
                    ++this.baseMagicNumber;
                }
            }
        }

        if (this.baseMagicNumber > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }

    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (this.baseMagicNumber > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }

        this.initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }

    }

    public AbstractCard makeCopy() {
        return new ThunderStrike();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Thunder Strike");
    }
}
