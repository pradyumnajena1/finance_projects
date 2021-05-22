package com.pd.finance.view;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractView<T> implements IView<T>{
   private IView<T> decoratedView;

   public AbstractView() {
   }

   public AbstractView(IView<T> decoratedView) {
      this.decoratedView = decoratedView;
   }

   @Override
   public Map<String,IViewAttribute<T>> getAttributeMap() {
      Map<String,IViewAttribute<T>> attributeMap = null;
      if(decoratedView!=null){
          attributeMap = decoratedView.getAttributeMap();
      }else {
         attributeMap = new HashMap<>();
      }
      collectViewAttributes(attributeMap);
      return attributeMap;
   }

   protected abstract void collectViewAttributes(Map<String, IViewAttribute<T>> attributeMap);


}
