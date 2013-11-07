CSRF tests issue test case
==========================

Testing controllers that implement CSRF protection does not work.

This simple application includes a single controller,
`CSRFController`.  This has GET and POST methods to render and process
a form. When a form submission is invalid, the form will typically be
re-rendered with the submission alongside details of any errors in the
form. The controller mimicks this by simply re-rendering the form in
the POST action.

This controller does work. The CSRF protection is in place and seems
to work as expected. Submitting the form reshows the form again, as if
the submission had failed. Since it is a reduced test case, I have not
included any other fields on the form to focus attention on the CSRF
problem.

Grab this application and try running the tests in sbt.

    $ sbt
	...
	[csrftest] $ test

You'll see that the problem is that testing this controller results in
a `RuntimeException: Missing CSRF Token (csrf.scala:51)`. We can see
the line that triggers this error in Play's source.

    CSRF.getToken(request).getOrElse(sys.error("Missing CSRF Token"))

Clearly it has failed to extract the token from the request. The test
is run the way the documentation suggests (see
[the testing docs](http://www.playframework.com/documentation/2.2.1/),
"Testing a controller") and the controller is set up the way the CSRF
documentation describes for a non-global implementation
([the ScalaCSRF docs](http://www.playframework.com/documentation/2.2.1/ScalaCsrf)). The
problem emerges when the view is rendered. It tries to get the CSRF
token so it can populate the CSRF form field.

I have included 2 test cases. The first shows this problem with a
controller method written as described in the documentation. The
second uses a controller that has both CSRFAddToken and CSRFCheck
action wrappers. This works because it forces Play to add the token to
the request. It also doesn't appear to impact the CSRF protection's
correctness. If this is the case, perhaps the CSRFAddToken
functionality should be included in the CSRFCheck action wrapper so
that testing controllers works.

However, since the documented implementation does actually work (it
just doesn't work in the tests), I think this is a bug in the way the
test are run with a `FakeRequest` and `FakeApplication`.

If I've missed something obvious please let me know.

Thanks very much!
