#ifdef GL_SL
precision mediump float;
#endif

uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


// Created by mmichal

void mainImage( out vec4 fragColor, in vec2 fragCoord )

{
    vec2 uv = fragCoord.xy;
	vec4 col = texture2D( iChannel0, uv );

	//Tritanopia ("blue/yellow", <1% of males and females)
	vec3 c_r = vec3(0.95, 0.05, 0.);
	vec3 c_g = vec3(0., 0.43333, 0.56667);
	vec3 c_b = vec3(0., 0.475, 0.525);

	vec3 rgb = vec3( dot(col.rgb ,c_r), dot(col.rgb ,c_g), dot(col.rgb ,c_b));

	fragColor = vec4(rgb,1.0);
}

void main() {
	mainImage(gl_FragColor, texCoord);
}
