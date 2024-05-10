import data.CrossConfigDevice
import platform.UIKit.UIScreen
import platform.UIKit.UITraitCollection
import platform.UIKit.UIUserInterfaceStyle

class CrossConfigDevice: CrossConfigDevice{
    override fun isDarModeEnabled(): Boolean {
        val iOSTheme: UITraitCollection = UIScreen.mainScreen.traitCollection
        return iOSTheme.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
    }

}