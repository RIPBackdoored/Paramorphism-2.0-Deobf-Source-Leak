package org.jline.reader.impl;

import java.util.function.Consumer;

public class UndoTree {
   private final Consumer state;
   private final UndoTree$Node parent;
   private UndoTree$Node current;

   public UndoTree(Consumer var1) {
      super();
      this.state = var1;
      this.parent = new UndoTree$Node(this, (Object)null);
      UndoTree$Node.access$002(this.parent, this.parent);
      this.clear();
   }

   public void clear() {
      this.current = this.parent;
   }

   public void newState(Object var1) {
      UndoTree$Node var2 = new UndoTree$Node(this, var1);
      UndoTree$Node.access$102(this.current, var2);
      UndoTree$Node.access$002(var2, this.current);
      this.current = var2;
   }

   public boolean canUndo() {
      return UndoTree$Node.access$000(this.current) != this.parent;
   }

   public boolean canRedo() {
      return UndoTree$Node.access$100(this.current) != null;
   }

   public void undo() {
      if (!this.canUndo()) {
         throw new IllegalStateException("Cannot undo.");
      } else {
         this.current = UndoTree$Node.access$000(this.current);
         this.state.accept(UndoTree$Node.access$200(this.current));
      }
   }

   public void redo() {
      if (!this.canRedo()) {
         throw new IllegalStateException("Cannot redo.");
      } else {
         this.current = UndoTree$Node.access$100(this.current);
         this.state.accept(UndoTree$Node.access$200(this.current));
      }
   }
}
