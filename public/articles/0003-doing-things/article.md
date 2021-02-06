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

# Ok, great, I have a certificate now! _What do I do with it?_

Great question. So it turns out you end up with a few PEM's a the end of the CertBot portion
of this exercise. 

```shell script
$  sudo ls /etc/letsencrypt/live/$YOUR_DOMAIN_NAME_HERE
README	cert.pem  chain.pem  fullchain.pem  privkey.pem
```

So those are files. What's a ~~Paladin~~ PEM?

> There are several commonly used filename extensions for X.509 certificates. Unfortunately, some of these extensions are also used for other data such as private keys.
> 
> * .pem – (Privacy-enhanced Electronic Mail) Base64 encoded DER certificate, enclosed between "-----BEGIN CERTIFICATE-----" and "-----END CERTIFICATE-----"
> * .cer, .crt, .der – usually in binary DER form, but Base64-encoded certificates are common too (see .pem above)
> * .p7b, .p7c – PKCS#7 SignedData structure without data, just certificate(s) or CRL(s)
> * .p12 – PKCS#12, may contain certificate(s) (public) and private keys (password protected)
> * .pfx – PFX, predecessor of PKCS#12 (usually contains data in PKCS#12 format, e.g., with PFX files generated in IIS)
> * PKCS#7 is a standard for signing or encrypting (officially called "enveloping") data. Since the certificate is needed to verify signed data, it is possible to include them in the SignedData structure. A .P7C file is a degenerated SignedData structure, without any data to sign.[citation needed]
> 
> PKCS#12 evolved from the personal information exchange (PFX) standard and is used to exchange public and private objects in a single file.[citation needed]

Ok so.. it's a file type for a.. X.509?

> In cryptography, X.509 is a standard defining the format of public key certificates.[1] 
> X.509 certificates are used in many Internet protocols, including TLS/SSL, which is the basis for 
> HTTPS,[2] the secure protocol for browsing the web. They are also used in offline applications, 
> like electronic signatures. An X.509 certificate contains a public key and an identity (a hostname,
>  or an organization, or an individual), and is either signed by a certificate authority or 
> self-signed. When a certificate is signed by a trusted certificate authority, or validated by other 
> means, someone holding that certificate can rely on the public key it contains to establish secure 
> communications with another party, or validate documents digitally signed by the corresponding 
> private key.

Oh. Got it. Particular file format for signatures and keys. Fancy. So I've got one of these, and I 
want to make my Javalin instance all secure and stuff. [The docs](https://javalin.io/documentation#server-setup)
say to [go here and follow the sample](https://github.com/tipsy/javalin-http2-example/).


```kotlin
// blob/master/src/main/kotlin/app/Main.kt

private fun createHttp2Server(): Server {

    val alpn = ALPNServerConnectionFactory().apply {
        defaultProtocol = "h2"
    }

    val sslContextFactory = SslContextFactory().apply {
        keyStorePath = Main::class.java.getResource("/keystore.jks").toExternalForm() // replace with your real keystore
        setKeyStorePassword("password") // replace with your real password
        cipherComparator = HTTP2Cipher.COMPARATOR
        provider = "Conscrypt"
    }

    val ssl = SslConnectionFactory(sslContextFactory, alpn.protocol)

    val httpsConfig = HttpConfiguration().apply {
        sendServerVersion = false
        secureScheme = "https"
        securePort = 8443
        addCustomizer(SecureRequestCustomizer())
    }

    val http2 = HTTP2ServerConnectionFactory(httpsConfig)

    val fallback = HttpConnectionFactory(httpsConfig)

    return Server().apply {
        //HTTP/1.1 Connector
        addConnector(ServerConnector(server).apply {
            port = 8080
        })
        // HTTP/2 Connector
        addConnector(ServerConnector(server, ssl, alpn, http2, fallback).apply {
            port = 8443
        })
    }

}
```

That all seems reasonable except... wait... `.jks`? *_I DON'T HAVE A JKS I HAVE A PEM I HATE COMPUTERS._*.
So you search for that and you might find something like 
[the ancient tomes from the unspoken one](https://docs.oracle.com/cd/E35976_01/server.740/es_admin/src/tadm_ssl_convert_pem_to_jks.html).

> This topic describes how to convert PEM-format certificates to the standard Java KeyStore (JKS) format.

Oh we're about to get to _sorceror levels of magical_, aren't we? I hate computers. I copied this page
into a gist because hey it's free to copy these days and 
[why not make more noise on the internet](https://gist.github.com/tikimcfee/01b6a4a0f98d6f657e12ef6a46892482)?
The general idea is this:

* `openssl`; "... run the openssl utility from the directory of your choice."
* `keytool`; "Your path will allow you to use the keytool utility from the directory of your choice."
* All of the input files are located in the local directory.

Let's begin.

```shell script

```