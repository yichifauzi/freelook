package freelook.freelook.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.atomic.AtomicBoolean;

@Environment(EnvType.CLIENT)
public class FreelookmodClient implements ClientModInitializer {

    public static boolean isFreeLooking = false;
    public static boolean isToggle = false;
    private static Perspective lastPerspective;

    @Override
    public void onInitializeClient() {

        KeyBinding freeLook = KeyBindingHelper.registerKeyBinding(new KeyBinding("FreeLook_HoldKey", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "FreeLookMod"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
        if(!isToggle) {
            if (freeLook.isPressed()) {
                if (!isFreeLooking) { // only execute when starting to freelook
                    lastPerspective = client.options.getPerspective();

                    // switch from first to third person
                    if (lastPerspective == Perspective.FIRST_PERSON) {
                        client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                    }

                    isFreeLooking = true;
                }
            } else if (isFreeLooking) { // only execute when stopping to freelook
                isFreeLooking = false;
                client.options.setPerspective(lastPerspective);
            }
        }
        else {
            if (freeLook.wasPressed()) {
                if (!isFreeLooking) { // only execute when starting to freelook
                    lastPerspective = client.options.getPerspective();

                    // switch from first to third person
                    if (lastPerspective == Perspective.FIRST_PERSON) {
                        client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                    }

                    isFreeLooking = true;
                } else if (freeLook.isPressed()) {
                    isFreeLooking = false;
                    client.options.setPerspective(lastPerspective);
                }
            }
        }



        });


    }


}