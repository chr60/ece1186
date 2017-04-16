package TrackModel; 

import java.util.ArrayList;

public class Switch {
  TrackModel superTrackModel;
  Boolean switchState;
  String switchName;
  Block rootBlock;
  ArrayList<Block> leafBlocks;

  public Switch(TrackModel trackModel, String switchName, Block rootBlock, ArrayList<Block> leafBlocks) {
    this.superTrackModel = trackModel;
    this.rootBlock = rootBlock;
    this.leafBlocks = leafBlocks;
    this.switchName = switchName;
    this.switchState = true;
  }

  /**
  * Allows for modification of the switchState of a switch.
  */
  public void setSwitchState(Boolean newState) {
    this.switchState = newState;
  }

  /**
  * Allows viewing of the switch state by other modules.
  */
  public Boolean switchState() {
    return this.switchState;
  }

  /**
  * Returns the nextBlock when calling a switch. If the switch is true, and is called
  * on a root, it returns the lower leaf block. If called on the lower leaf, it returns 
  * the root. If called on the higher leaf, returns the higher leaf. If false and is called on root,
  * returns the higher numbered block. If called on the lower leaf, returns the lower leaf.
  * If called on the higher block returns root.
  */
  public Block nextBlock(Block currBlock) {
    if (this.switchState.equals(true)) {
      if (currBlock.equals(rootBlock)){
        return this.leafBlocks.get(0);
      } else if (currBlock.equals(leafBlocks.get(0))) {
          return this.rootBlock;
       } else {
          return currBlock;
        }
    } else {
        if (currBlock.equals(rootBlock)) {
          return this.leafBlocks.get(1);
        } else if (currBlock.equals(leafBlocks.get(0))) {
            return currBlock;
        } else {
          return this.rootBlock;
      }
    }
  }
}