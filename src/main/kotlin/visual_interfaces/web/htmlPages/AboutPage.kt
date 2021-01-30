package visual_interfaces.web.htmlPages

import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.SimpleHTML.div
import visual_interfaces.web.htmlComponents.SimpleHTML.localImage
import visual_interfaces.web.htmlComponents.SimpleHTML.setCssClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.text
import visual_interfaces.web.htmlComponents.ComponentClasses.AboutPage.devImage
import visual_interfaces.web.htmlComponents.ComponentClasses.MainPage.mainBodyWrapper
import visual_interfaces.web.htmlComponents.SimpleHTML.h2
import visual_interfaces.web.htmlComponents.SimpleHTML.h3
import visual_interfaces.web.htmlComponents.SimpleHTML.p

fun SimpleHTML.Html.makeAboutPageContent() {
    setCssClasses(mainBodyWrapper)

    localImage(
       "WhatAMug.jpg",
       "A goofy image of the developer offering a fist bump while hunching with duckface."
    ) { setCssClasses(devImage) }

    h2 { text("You find a Lugo. It speaks to you in Common.") }
    h3 { text("There are exits Back and North. You have 3 internet points.") }

    homePagesIntro.forEach {
        p { text(it) }
    }
}

val homePagesIntro by lazy {
    arrayOf(
       """
Welp, you've found some words on the internet. Not quite sure how ya got here, 
but in general, you're probably welcome.
""",

       """
Probably. I mean just assume you are for now and we'll deal with you not being
welcome later if it comes to it, shall we?
""",

       "This has gotten a bit complicated. Let's start over.",

       """
Hello! I'm a Lugo! There are some of us out there, but we are few and far between. 
I am not, in fact, a province, nor am I a conglomeration of smaller entities masquerading 
as a Common-speaking computer programmer because that would just be ridiculous right - of course 
it would. So we can move on knowing that together.
""",

       """
I still feel like we're not quite there yet. Tell ya what. You're free to roam around the links 
here. Read some words. There are plenty of them. I'd prefer if you *not* try to do things that 
could make the words broken - ya know, attacking the tiny server this is running on, dropping 
millions of lines of malicious code intent on spreading itself through the very network 
you were let in on, or even finding ways to access other interesting devices on the network 
you may find doing things I didn't know you could do with computers. 
I mean it's just a preference. Good taste and all that?
""",

       "Hmm, still complicated.",

       "Anyway.",

       "So yeah here you are. Have fun! Or don't. I dunno what your style is."
    ).map { it.trimIndent() }
}