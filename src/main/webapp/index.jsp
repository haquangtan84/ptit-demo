<!doctype html>

<html>
<head>
    <meta charset="utf-8">
    <title>JAX-RS Template</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="http://getbootstrap.com/2.3.2/assets/css/bootstrap.css" rel="stylesheet">
    <link href="http://getbootstrap.com/2.3.2/assets/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="http://getbootstrap.com/2.3.2/assets/js/google-code-prettify/prettify.css" rel="stylesheet">

    <!--
    IMPORTANT:
    This is Heroku specific styling. Remove to customize.
    -->
    <link href="http://heroku.github.com/template-app-bootstrap/heroku.css" rel="stylesheet">
    <style type="text/css">
        .instructions { display: none; }
        .instructions li { margin-bottom: 10px; }
        .instructions h2 { margin: 18px 0;}
        .instructions blockquote { margin-top: 10px; }
        .screenshot {
            margin-top: 10px;
            display:block;
        }
        .screenshot a {
            padding: 0;
            line-height: 1;
            display: inline-block;
            text-decoration: none;
        }
        .screenshot img, .tool-choice img {
            border: 1px solid #ddd;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075);
            -moz-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075);
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075);
        }
    </style>
    <!-- /// -->
    <script type="text/javascript">
        <!--
        function appname() {
            return location.hostname.substring(0,location.hostname.indexOf("."));
        }

        function openRestClient(startUrl) {
            $('#rest-url').val(startUrl);
            $('#rest-result-container').hide();
            $('#rest-client-tab').click();
        }

        function sendGetRequest() {
            $.ajax({
                url: $('#rest-url').val(),
                dataType:'json',
                context: document.body,
                success: function(data, textStatus, jqXHR) {
                    showRestResults('Success', 'alert-success', JSON.stringify(data, undefined, 2));
                },
                error: function(qXHR, textStatus, errorThrown) {
                    if (errorThrown instanceof SyntaxError) {
                        errorThrown = errorThrown.message;
                    }

                    showRestResults(textStatus, 'alert-error', errorThrown);
                }
            });
        }

        function showRestResults(headerText, headerClass, bodyText) {
            $('#rest-result-header-text').text(headerText);
            $('#rest-result-header').removeClass('alert-error').removeClass('alert-success');
            $('#rest-result-header').addClass(headerClass);

            $('#rest-result-body').text(bodyText);
            prettyPrint();

            $('#rest-result-container').show();
        }

        function prettyPrintOnce() {
            prettyPrint();
            $('.prettyprint.once').removeClass('prettyprint');
        }
        // -->
    </script>
</head>

<body onload="prettyPrintOnce();">

<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a href="/" class="brand">JAX-RS Template</a>
            <!--
            IMPORTANT:
            This is Heroku specific markup. Remove to customize.
            -->
            <a href="/" class="brand" id="heroku">by <strong>heroku</strong></a>
            <!-- /// -->
        </div>
    </div>
</div>

<div class="container" id="getting-started">
<div class="row">
<div class="span8 offset2">
<h1 class="alert alert-success">Your app is ready!</h1>

<div class="page-header">
    <h1>Get started with JAX-RS</h1>
</div>

<div style="margin-bottom: 20px">
    This is a template for a lightweight RESTful API using JAX-RS. 
    The sample code is a call for getting the current time. 
    To try it out use the example REST client below to send a <code>GET</code> request to <code>/services/time</code>. 
    Then use Eclipse or the Command Line to deploy some changes. 
</div>

<ul id="tab" class="nav nav-tabs">
    <li class="active"><a id="eclipse-instructions-tab" href="#eclipse-instructions" data-toggle="tab">Use Eclipse 3.7</a></li>
    <li><a id="cli-instructions-tab" href="#cli-instructions" data-toggle="tab">Use Command Line</a></li>
    <li><a id="rest-client-tab" href="#rest-client" data-toggle="tab">Example REST Client</a></li>
</ul>

<div class="tab-content">

<div id="eclipse-instructions" class="instructions tab-pane active">
    <a name="using-eclipse"></a>
    <h1>Using Eclipse 3.7:</h1>

    <h2>Step 1. Setup your environment</h2>
    <ol>
        <li>Ensure <a href="http://unicase.blogspot.com/2011/01/egit-tutorial-for-beginners.html">EGit</a> is installed.</li>
        <li>Ensure the <a href="http://www.eclipse.org/m2e/">Maven Eclipse Plugin</a> is installed.</li>
        <li>Create an SSH key if you haven't already:
            <ol>
                <li>Go to <code>Window</code> <i class="icon-chevron-right"></i> <code>Preferences</code> <i class="icon-chevron-right"></i> <code>General</code> <i class="icon-chevron-right"></i> <code>Network Connections</code> <i class="icon-chevron-right"></i> <code>SSH2</code></li>
                <li>Choose the <code>Key Management</code> tab</li>
                <li>Click <code>Generate RSA Key...</code>
                    <div class="modal hide" id="addingSshKey">
                        <div class="modal-header">
                            <a class="close" data-dismiss="modal">x</a>
                            <h3>Generate RSA Key</h3>
                        </div>
                        <div class="modal-body">
                            <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/2-3-adding-ssh-key.png" alt="SSH Eclipse Preferences Window" />
                        </div>
                    </div>
                    <span class="screenshot">
                      <a href="#addingSshKey" data-toggle="modal">
                          <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/2-3-adding-ssh-key.png" alt="SSH Eclipse Preferences Window" width="100" />
                          <i class="icon-zoom-in"></i>
                      </a>
                    </span>
                </li>
                <li>Copy the generated public key in the text box and <a href="https://api.heroku.com/account/ssh" class="btn btn-primary btn-mini">add it to your account</a></li>
                <li>Click <code>Save Private Key...</code>, accepting the defaults</li>
                <li>Click <code>Ok</code></li>
            </ol>
        </li>
    </ol>

    <h2>Step 2. Clone the App</h2>
    <ol>
        <li>Go to <code>File</code> <i class="icon-chevron-right"></i> <code>Import...</code> <i class="icon-chevron-right"></i> <code>Git</code> <i class="icon-chevron-right"></i> <code>Projects from Git</code>
            <div class="modal hide" id="importFromGit">
                <div class="modal-header">
                    <a class="close" data-dismiss="modal">x</a>
                    <h3>Import</h3>
                </div>
                <div class="modal-body">
                    <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/2-1-import.png" alt="Import" />
                </div>
            </div>
                <span class="screenshot">
                  <a href="#importFromGit" data-toggle="modal">
                      <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/2-1-import.png" alt="Import" width="100" />
                      <i class="icon-zoom-in"></i>
                  </a>
                </span>
        </li>
        <li>Choose <code>URI</code> and click <code>Next</code></li>
        <li>Enter <code>git@heroku.com:<script>document.write(appname());</script>.git</code> in the <code>URI</code> field.
            <div class="modal hide" id="cloneGitRepository">
                <div class="modal-header">
                    <a class="close" data-dismiss="modal">x</a>
                    <h3>Clone Git Repository</h3>
                </div>
                <div class="modal-body">
                    <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/3-4-clone-git-repo.png" alt="Clone Git Repository" />
                </div>
            </div>
                <span class="screenshot">
                  <a href="#cloneGitRepository" data-toggle="modal">
                      <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/3-4-clone-git-repo.png" alt="Clone Git Repository" width="100" />
                      <i class="icon-zoom-in"></i>
                  </a>
                </span>
        </li>
        <li>Click <code>Next</code> three times
            <blockquote>
                Click <code>Yes</code> to the question of authenticity if the question appears.
            </blockquote>
        </li>
        <li>Choose <code>Import as general project</code>
            <div class="modal hide" id="importProjectsFromGit">
                <div class="modal-header">
                    <a class="close" data-dismiss="modal">x</a>
                    <h3>Import Projects from Git</h3>
                </div>
                <div class="modal-body">
                    <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/2-6-import-general.png" alt="Import Projects from Git" />
                </div>
            </div>
                <span class="screenshot">
                  <a href="#importProjectsFromGit" data-toggle="modal">
                      <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/2-6-import-general.png" alt="Import Projects from Git" width="100" />
                      <i class="icon-zoom-in"></i>
                  </a>
                </span>
        </li>
        <li>Click <code>Finish</code></li>
    </ol>

    <h2>Step 3. Configure the App</h2>
    <ol>
        <li>Right-click the project root</li>
        <li>Choose <code>Configure</code> <i class="icon-chevron-right"></i> <code>Convert to Maven Project</code> </li>
    </ol>

    <h2>Step 4. Makes some changes to the app</h2>
    <ol>
        <li>Open <code>src/main/java/com/example/services/TimeService.java</code></li>
        <li>To add a new resource path that supports specific time zones (e.g. <code>GET /services/time/est</code> for the time in Eastern Standard Time), add this method:
            <pre class="prettyprint once language-java">
@GET
@Path("/{timezone}")
public Time get(@PathParam("timezone") String timezone) {
    return new Time(TimeZone.getTimeZone(timezone.toUpperCase()));
}</pre>

            Be sure to also add the necessary import statements:
            <pre class="prettyprint once language-java">
import javax.ws.rs.PathParam;
import java.util.TimeZone;
</pre>
        </li>
    </ol>

    <h2>Step 5. Deploy to Heroku</h2>
    <ol>
        <li>Right-click the project root and choose <code>Team</code> <i class="icon-chevron-right"></i> <code>Commit</code></li>
        <li>Enter a commit message and click <code>Commit</code>
            <div class="modal hide" id="commitChanges">
                <div class="modal-header">
                    <a class="close" data-dismiss="modal">x</a>
                    <h3>Commit Changes</h3>
                </div>
                <div class="modal-body">
                    <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/6-5-commit.png" alt="Commit Changes" />
                </div>
            </div>
                <span class="screenshot">
                  <a href="#commitChanges" data-toggle="modal">
                      <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/6-5-commit.png" alt="Commit Changes" width="100" />
                      <i class="icon-zoom-in"></i>
                  </a>
                </span>
        </li>
        <li>Right-click the project root and choose <code>Team</code> <i class="icon-chevron-right"></i> <code>Push to Upstream</code></li>
        <li>Review the push results. At the bottom, a "... deployed to Heroku" message will appear.
            <div class="modal hide" id="pushResults">
                <div class="modal-header">
                    <a class="close" data-dismiss="modal">x</a>
                    <h3>Push Results</h3>
                </div>
                <div class="modal-body">
                    <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/6-8-push-result.png" alt="Push Results" />
                </div>
            </div>
                <span class="screenshot">
                  <a href="#pushResults" data-toggle="modal">
                      <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/6-8-push-result.png" alt="Push Results" width="100" />
                      <i class="icon-zoom-in"></i>
                  </a>
                </span>
        </li>
    </ol>

    <div class="hero-unit">
        <h1>Done!</h1>
        <p>You've just cloned, modified, and deployed a brand new app.</p>
        <a href="#" onclick="openRestClient('/services/time/est');" class="btn btn-primary btn-large">See your changes</a>
                
        <p style="margin-top: 20px">Learn more at the   
        <a href="http://devcenter.heroku.com/categories/java">Heroku Dev Center</a></p>
    </div>
</div> <!-- End Eclipse tab-->

<div id="cli-instructions" class="instructions tab-pane">
    <a name="using-cli"></a>
    <h1>Using the Command Line:</h1>

    <h2>Step 1. Setup your environment</h2>
    <ol>
        <li>Install the <a href="http://toolbelt.heroku.com">Heroku Toolbelt</a>.</li>
        <li>Install <a href="http://maven.apache.org/download.html">Maven</a>.</li>
    </ol>

    <h2>Step 2. Login to Heroku</h2>
    <code>heroku login</code>
    <blockquote>
        Be sure to create, or associate an SSH key with your account.
    </blockquote>
            <pre>
$ heroku login
Enter your Heroku credentials.
Email: naaman@heroku.com
Password:
Could not find an existing public key.
Would you like to generate one? [Yn] Y
Generating new SSH public key.
Uploading SSH public key /Users/Administrator/.ssh/id_rsa.pub
Authentication successful.</pre>

    <h2>Step 3. Clone the App</h2>
    <code>git clone -o heroku git@heroku.com:<script>document.write(appname())</script>.git</code>

    <h2>Step 4. Makes some changes to the app</h2>
    <ol>
        <li>Open <code>src/main/java/com/example/services/TimeService.java</code> in your favorite editor.</li>
        <li>To add a new resource path that supports specific time zones (e.g. <code>GET /services/time/est</code>
            for the time in Eastern Standard Time), add this method:
            <pre class="prettyprint once language-java">
@GET
@Path("/{timezone}")
public Time get(@PathParam("timezone") String timezone) {
    return new Time(TimeZone.getTimeZone(timezone.toUpperCase()));
}</pre>

            Be sure to also add the necessary import statements:
            <pre class="prettyprint once language-java">
import javax.ws.rs.PathParam;
import java.util.TimeZone;
</pre>
        </li>
    </ol>

    <h2>Step 5. Make sure the app still compiles</h2>
    <code>mvn clean package</code>

    <h2>Step 6. Deploy your changes</h2>
    <ol>
        <li><code>git commit -am "New changes to deploy"</code></li>
        <li><code>git push heroku master</code></li>
    </ol>

    <div class="hero-unit">
        <h1>Done!</h1>
        <p>You've just cloned, modified, and deployed a brand new app.</p>
        <a href="#" onclick="openRestClient('/services/time/est');" class="btn btn-primary btn-large">See your changes</a>
                
        <p style="margin-top: 20px">Learn more at the   
        <a href="http://devcenter.heroku.com/categories/java">Heroku Dev Center</a></p>
    </div>
</div> <!-- End CLI tab -->

<div id="rest-client" class="tab-pane">
    <h1>REST Client</h1>
    <div id="rest-client-widget" style="margin: 10px">
        <form class="form-horizontal" class="row-fluid">
            <fieldset>
                <label class="control-label search-query" for="rest-url"><strong>URL</strong></label>
                <input id="rest-url" type="text" value="/services/time">
                <button type="submit" class="btn btn-primary" onclick="sendGetRequest(); return false;">GET</button>
            </fieldset>
        </form>

        <div id="rest-result-container" class="row hide">
            <div class="row">
                <div id="rest-result-header" class="offset2 span1 alert">
                    <a id="rest-result-close-btn" class="close" data-dismiss="alert" onclick="$('#rest-result-container').hide();">×</a>
                    <span id="rest-result-header-text"></span>
                </div>
            </div>
            <div class="row">
                <pre id="rest-result-body" class="offset2 span4 prettyprint language-json"></pre>
            </div>
        </div>
    </div>
</div> <!-- end Rest Client tab -->

</div> <!-- end tab content -->
</div>



</div>


</div>

</div>
</div>
</div>

<script src="http://getbootstrap.com/2.3.2/assets/js/jquery.js"></script>
<script src="http://getbootstrap.com/2.3.2/assets/js/bootstrap-tab.js"></script>
<script src="http://getbootstrap.com/2.3.2/assets/js/bootstrap-modal.js"></script>
<script src="http://getbootstrap.com/2.3.2/assets/js/google-code-prettify/prettify.js"></script>
</body>
</html>
