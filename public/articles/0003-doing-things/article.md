# So here's what I know

https://ericdraken.com/individually-styled-markdown-elements/

* The Raspberry Pi should be the thing I host these articles on for a while.
* The Pi will directly wired in to the local network, and be forwarded through a router (or two).
* The Pi should supports HTTPS insofar as it is possible to implement to reasonable standards.
* I am winging absolutely everything.
* I should document everything I do that does something.
* Or doesn't.

> Quick shortcut for the links on the page
>
> [LetsEncrypt](https://letsencrypt.org/)
> [CertBot, Root](https://certbot.eff.org/)
> [CertBot, Intro docs](https://certbot.eff.org/docs/intro.html)  
> [Javalin](https://javalin.io/)

```shell script
# I used these to do the stuff on this page

# Find local network ip. Experiment with it.
$ sudo lshw -class network | grep -i "ip="
$ ip a
```

So first off, here's I am now. I know I want to support a secure connection to these pages. I'm not
collecting any kind of information, but it seems to be a good practice. To that end, I've gotten
as far as acquiring a legitimate certificate from (LetsEncrypt)[https://letsencrypt.org/]. I did
this on *not* the Pi because the Pi was experiencing a major existential crisis in terms of not
being logged in to a user session, ergo not connected to the network. Probably.

Anyway.

I know about this thing called [CertBot](https://certbot.eff.org/docs/intro.html). It's an automated 
tool that... well, this is what is does:

>
> Certbot is an easy-to-use client that fetches a certificate from Let’s Encrypt—an open certificate
> authority launched by the EFF, Mozilla, and others—and deploys it to a web server.  
>> Very noble
>
> Anyone who has gone through the trouble of setting up a secure website knows what a hassle getting 
> and maintaining a certificate is. Certbot and Let’s Encrypt can automate away the pain and let you 
> turn on and manage HTTPS with simple commands. Using Certbot and Let’s Encrypt is free, so there’s 
> no need to arrange payment.
>> After you go through some of the steps to get this working, and get a personal look at the output
>> of the scripts, you'll most certainly appreciate the work put in to automation.
>

At [CertBot's home page](https://certbot.eff.org/), there's a nice little interactive guide that 
lists out fairly reasonably easy-to-follow instructions if you're one to get down and dangerous with
a command shell. It didn't work for me. In my case, I'm using a Java-based server called 
[Javalin](https://javalin.io/). It's a nice little Kotlin layer over [Jetty](https://www.eclipse.org/jetty/).
There's a lot there to look over, there, but let's just assume we're at the point of serving pages.

So first, let's get CertBot onto the Pi:

```shell script
# Get the package manager so we can get the package.. oof.
$ sudo snap install core; sudo snap refresh core

# Ok so here's the thing we actually want. 'classic' is suggested in the tutorial.
$ sudo snap install --classic certbot
certbot 1.12.0 from Certbot Project (certbot-eff✓) installed

# Now link(sym).
$ sudo ln -s /snap/bin/certbot /usr/bin/certbot

# Ok, since there's no server running, we can use the standalone version. Let's see.
$ sudo certbot certonly --standalone
Saving debug log to /var/log/letsencrypt/letsencrypt.log
Plugins selected: Authenticator standalone, Installer None
Please enter in your domain name(s) (comma and/or space separated)  (Enter 'c'
to cancel): allthelugos.com
Requesting a certificate for allthelugos.com
Performing the following challenges:
http-01 challenge for allthelugos.com
Waiting for verification...
Cleaning up challenges

IMPORTANT NOTES:
 - Congratulations! Your certificate and chain have been saved at:
   /etc/letsencrypt/live/allthelugos.com/fullchain.pem
   Your key file has been saved at:
   /etc/letsencrypt/live/allthelugos.com/privkey.pem
   Your certificate will expire on 2021-05-07. To obtain a new or
   tweaked version of this certificate in the future, simply run
   certbot again. To non-interactively renew *all* of your
   certificates, run "certbot renew"
 - If you like Certbot, please consider supporting our work by:

   Donating to ISRG / Let's Encrypt:   https://letsencrypt.org/donate
   Donating to EFF:                    https://eff.org/donate-le
```

Ok, great, I have a certificate now! _What do I do with it?_

