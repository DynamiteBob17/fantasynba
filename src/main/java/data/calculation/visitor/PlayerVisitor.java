package data.calculation.visitor;

import data.Player;

public interface PlayerVisitor {
    void visit(Player player);
}
