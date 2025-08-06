package mojichimera.powers;


import Naijiumod.utils.FormatHelper;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
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

//@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class DarkProtocolPower extends AbstractPower implements NonStackablePower {
    public static final String POWER_ID = mojichimera.makeID(DarkProtocolPower.class.getSimpleName());

    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private final AbstractCard card;
//    public static final SpireField<Boolean> isDarkProtocol = new SpireField<>(() -> false);

    public DarkProtocolPower(AbstractCreature owner, AbstractCard card, int amount) {
        this.name = TEXT.NAME;
        this.ID = POWER_ID;
        this.amount = amount;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.card = card;
        this.isTurnBased = false;
        this.loadRegion("darkembrace");
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(TEXT.DESCRIPTIONS[0], this.amount, FormatHelper.prefixWords(this.card.name, "#y"));

        if (this.description.contains("copyreplace")) {
            this.description = this.description.replace("copyreplace", (amount == 1) ? "copy" : "copies");
        }
    }

    @Override
    public void onExhaust(AbstractCard _card) {
//        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && !isDarkProtocol.get(_card)) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            this.addToBot(new AbstractGameAction() {
                public void update() {
                    for (int i = 0; i < DarkProtocolPower.this.amount; ++i) {
                        AbstractCard tmp = DarkProtocolPower.this.card.makeSameInstanceOf();
                        AbstractDungeon.player.limbo.addToBottom(tmp);
                        tmp.current_x = DarkProtocolPower.this.card.current_x;
                        tmp.current_y = DarkProtocolPower.this.card.current_y;
                        tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                        tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                        tmp.purgeOnUse = true;
//                        isDarkProtocol.set(tmp, true);
                        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, true, DarkProtocolPower.this.card.energyOnUse, true, true), true);
                    }

                    this.isDone = true;
                }
            });
        }
    }
}
