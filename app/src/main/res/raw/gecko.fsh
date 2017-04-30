#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


vec3 lighterColor( vec3 s, vec3 d )
{
	return (s.x + s.y + s.z > d.x + d.y + d.z) ? s : d;
}

vec3 lesRed(vec3 d ){

	//B&W Matrix
	vec3 c_r = vec3(.4, 0.3, 0.3);
	vec3 c_g = vec3(-0.2, 1., 0.2);
	vec3 c_b = vec3(0., 0., 1.);

    // swaping chanel red and blue
	d = vec3( dot(d.rgb,c_r), dot(d.rgb,c_g), dot(d.rgb,c_b) );

	return d;
}

vec3 redtoBlue(vec3 d ){

	//redtoBlue Matrix
	vec3 c_r = vec3(.5, 0.25, 0.25);
	vec3 c_g = vec3(0., .2, 0.);
	vec3 c_b = vec3(0., 0., .2);

	d = vec3( dot(d.rgb,c_b), dot(d.rgb,c_g), dot(d.rgb,c_r) );

	return d;
}

void mainImage( out vec4 fragColor, in vec2 fragCoord ){

    vec3 d = texture2D(iChannel0, fragCoord.xy / iResolution.xy).rgb;

    vec3 base = lesRed(d);
    vec3 fakeuv = redtoBlue(d);
    vec3 c = lighterColor(base, fakeuv);

	fragColor = vec4(c, 1.0);
}

void main() {
    mainImage(gl_FragColor, texCoord.xy*iResolution.xy);
}
