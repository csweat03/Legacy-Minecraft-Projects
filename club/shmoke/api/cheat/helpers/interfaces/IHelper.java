package club.shmoke.api.cheat.helpers.interfaces;

import club.shmoke.api.cheat.helpers.*;

/**
 * @author Kyle
 * @since 10/24/2017
 **/
public interface IHelper
{
    AngleHelper ANGLE_HELPER = new AngleHelper();
    BlockHelper BLOCK_HELPER = new BlockHelper();
    EntityHelper ENTITY_HELPER = new EntityHelper();
}
