package Naijiumod.cardModifier;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface DynvarCarrier {
  String key();
  
  int val(AbstractCard paramAbstractCard);
  
  int baseVal(AbstractCard paramAbstractCard);
  
  boolean modified(AbstractCard paramAbstractCard);
  
  boolean upgraded(AbstractCard paramAbstractCard);
}


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\DynvarCarrier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */