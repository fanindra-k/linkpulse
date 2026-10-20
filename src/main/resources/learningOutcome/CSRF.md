# CSRF (Cross-Site Request Forgery)

## What is CSRF?

- CSRF is an attack where a malicious website tricks a browser into performing an unwanted action on another website where the user is already logged in.
- The victim does not actively intend to send the request.
- The browser automatically attaches stored authentication data, which makes the attack possible.

## How the attack works

1. The user logs in to `mybank.com`.
2. The bank stores a session cookie in the browser.
3. The browser automatically sends that cookie with every request to `mybank.com`.
4. The user then visits a malicious site such as `evil-site.com`.
5. The malicious site submits a hidden form or script that triggers a request to the bank.
6. The browser sends the request with the bank's session cookie attached.
7. The bank processes the request as if it came from the legitimate user.

```html
<form action="https://mybank.com/transfer" method="POST">
    <input type="hidden" name="toAccount" value="attacker_account" />
    <input type="hidden" name="amount" value="5000" />
</form>
<script>document.forms[0].submit();</script>
```

- This is CSRF: the malicious site caused a valid-looking request to be sent using the victim's browser session.
- The server sees a real session cookie and assumes the action is valid.

![CSRF flow diagram](C:/Users/msi/AppData/Local/github-copilot/intellij/images/pasted_image_1_1792515706007.png)

## Why is CSRF disabled in LinkPulse?

- In `SecurityConfig.java`, the code disables CSRF using:

```java
.csrf(AbstractHttpConfigurer::disable)
```

- This is safe for LinkPulse because the application uses JWTs in an HTTP header, not cookies.
- Example:

```http
Authorization: Bearer <jwt-token>
```

### Why this prevents CSRF

- Browsers do not automatically attach custom headers like `Authorization` to cross-site requests.
- A malicious site cannot read the JWT from another origin due to the Same-Origin Policy (SOP).
- If the request reaches the server without a valid JWT header, it is rejected.

### Key takeaway

- CSRF is mainly a risk when credentials are automatically sent by the browser, such as cookies or HTTP Basic Auth.
- In LinkPulse, authentication requires an explicit header, so CSRF attacks are not possible in the same way.

## When should CSRF be enabled?

You must enable CSRF protection in these situations:

- Traditional web applications using `HttpSession` and `JSESSIONID`
- Server-rendered apps using form submissions (`<form method="POST">`)
- Modern SPAs that store JWTs in `HttpOnly` cookies and rely on browser-managed cookies for auth

### Scenario 1: Traditional web app

- The server stores login state in a session cookie.
- The browser sends it automatically on every request.
- CSRF protection is required.

### Scenario 2: Frontend using HttpOnly cookies

- If the app stores JWTs in an `HttpOnly` cookie and the browser automatically sends them,
- then CSRF protection must be enabled again.
- A common solution is the Double Submit Cookie pattern with:

```java
CookieCsrfTokenRepository.withHttpOnlyFalse()
```

## How CSRF protection works when enabled

- Spring Security issues a random CSRF token to the browser.
- The browser must include that token in the request.
- The server verifies the token before accepting the action.
- A malicious site cannot guess or read the token because of browser isolation.

### Example

```java
http.csrf(csrf -> csrf
    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
```

- If a malicious site tries to forge a request, it does not know the valid token.
- The server rejects the request.

## Final summary

- CSRF is a browser-based attack that abuses automatically attached cookies.
- LinkPulse is safe because it uses JWTs in headers instead of cookies.
- CSRF should only be enabled when the app depends on browser-sent cookies for authentication.
- If the app uses explicit Authorization headers, disabling CSRF is appropriate and safe.

```Interview Answer: "We disable CSRF because our REST API is stateless and uses token-based authentication via the Authorization: Bearer header. CSRF vulnerabilities exploit the browser's automatic inclusion of ambient credentials like cookies. Since custom headers cannot be forged cross-origin without direct access to the token, CSRF protection is redundant and would unnecessarily block non-browser clients like mobile apps and curl."```
